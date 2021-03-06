package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrackUtils;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.info.mkv.MKVInfo;
import com.nlogneg.transcodingService.info.mkv.MKVInfoFactory;
import com.nlogneg.transcodingService.info.mkv.MKVInfoSource;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.request.incoming.Selector;
import com.nlogneg.transcodingService.utilities.CollectionUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Projections;
import com.nlogneg.transcodingService.utilities.Tuple;

import fj.F;

public class DemultiplexJobFactory
{

	private static final Logger Log = LogManager.getLogger(DemultiplexJobFactory.class);
	private static final AtomicInteger IdSeed = new AtomicInteger();

	private enum MediaFileType
	{
		MKV, Other
	}

	/**
	 * Creates a new DemultiplexJob given a request
	 * 
	 * @param request
	 * @return
	 */
	public static Optional<? extends DemultiplexJob> tryCreateDemultiplexJob(
			final Request request,
			final MediaInfo mediaInfo,
			final MKVInfoSource source)
	{
		// Get tracks and summary
		final MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(mediaInfo);

		// Figure out what type of media file this is
		final Collection<GeneralTrack> generalTracks = summary.getGeneralTracks();
		final MediaFileType type = detectMediaFileType(generalTracks);

		switch (type)
		{
		case MKV:
			return Optional.make(createDemultiplexMKVJob(
					request,
					mediaInfo,
					summary,
					source));
		case Other:
			return Optional.make(new NoOpDemultiplexJob());
		default:
			throw new RuntimeException("Unknown media type detected");
		}
	}

	private static MediaFileType detectMediaFileType(
			final Collection<GeneralTrack> tracks)
	{
		if (tracks.size() < 1)
		{
			return MediaFileType.Other;
		}

		final GeneralTrack track = CollectionUtilities.first(tracks);
		final String format = track.getFormat();

		if ((format != null) && format.equalsIgnoreCase("Matroska"))
		{
			return MediaFileType.MKV;
		}

		return MediaFileType.Other;
	}

	private static DemultiplexMKVJob createDemultiplexMKVJob(
			final Request request,
			final MediaInfo mediaInfo,
			final MediaInfoTrackSummary summary,
			final MKVInfoSource source)
	{

		final Path sourceFile = Paths.get(request.getSourceFile());
		final Optional<MKVInfo> infoOptional = MKVInfoFactory.tryGetMKVInfo(
				sourceFile,
				source);

		// We can't get the info for some reason
		if (infoOptional.isNone())
		{
			Log.error("Could not create DemultiplexMKVJob for: " + request.getSourceFile());
			return null;
		}

		final MKVInfo info = infoOptional.getValue();

		final Collection<Attachment> allAttachments = info.getAttachments();
		final Collection<Attachment> fontAttachments = MKVDemultiplexingUtilities.getFontAttachments(allAttachments);

		final F<Attachment, Attachment> id = Projections.identity();
		final F<Attachment, Path> valueProj = new F<Attachment, Path>()
		{
			@Override
			public Path f(final Attachment a)
			{
				return Paths.get(a.getFileName());
			}
		};

		final Map<Attachment, Path> attachmentMap = CollectionUtilities.toMap(
				allAttachments,
				id,
				valueProj);
		final Map<Attachment, Path> fontAttachmentMap = CollectionUtilities.toMap(
				fontAttachments,
				id,
				valueProj);
		final Optional<Tuple<AudioTrack, Path>> audioTrackMap = deduceAudioTrack(
				request,
				summary);
		final Optional<Tuple<TextTrack, Path>> subtitleTrackMap = deduceSubtitleTrack(
				request,
				summary);

		return new DemultiplexMKVJob(
				sourceFile,
				mediaInfo,
				audioTrackMap,
				subtitleTrackMap,
				attachmentMap,
				fontAttachmentMap);
	}

	private static <T extends MediaTrack> Optional<Tuple<T, Path>> createTuple(
			final Request request,
			final T t)
	{
		final Path sourceFile = Paths.get(request.getSourceFile());
		final StringBuilder builder = new StringBuilder();

		builder.append(sourceFile.toAbsolutePath().toString()).append("_temp_").append(
				IdSeed.incrementAndGet()).append("_.").append(t.getFormat());

		return Optional.make(new Tuple<T, Path>(
				t,
				Paths.get(builder.toString())));
	}

	private static <T extends MediaTrack> Optional<Tuple<T, Path>> deduceTrack(
			final Request request,
			final Collection<T> tracks)
	{
		final Optional<T> chosenTrack = MKVDemultiplexingUtilities.tryDeduceMostLikelyTrack(tracks);
		if (chosenTrack.isNone())
		{
			return Optional.none();
		}

		return createTuple(request, chosenTrack.getValue());
	}

	private static Optional<Tuple<AudioTrack, Path>> deduceAudioTrack(
			final Request request,
			final MediaInfoTrackSummary summary)
	{
		final Selector selector = request.getSelector();
		if (selector.isForceUseAudioTrack())
		{
			final int audioTrack = selector.getAudioTrack();
			final Optional<AudioTrack> track = MediaTrackUtils.tryGetMediaTrackById(
					summary.getAudioTracks(),
					audioTrack);

			if (track.isSome())
			{
				return createTuple(request, track.getValue());
			}
		}
		return deduceTrack(request, summary.getAudioTracks());
	}

	private static Optional<Tuple<TextTrack, Path>> deduceSubtitleTrack(
			final Request request,
			final MediaInfoTrackSummary summary)
	{
		return deduceTrack(request, summary.getSubtitleTracks());
	}
}
