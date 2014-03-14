using System;
using System.Collections.Generic;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The MediaInfo XML node
	/// </summary>
	[Serializable]
	public sealed class MediaInfo
	{
		/// <summary>
		/// The VideoTracks XML node
		/// </summary>
		[YAXSerializeAs("VideoTracks")]
		public IEnumerable<VideoTrack> VideoTracks { get; set; }

		/// <summary>
		/// The AudioTracks XML node
		/// </summary>
		[YAXSerializeAs("AudioTracks")]
		public IEnumerable<AudioTrack> AudioTracks { get; set; }

		/// <summary>
		/// The SubtitleTracks XML node
		/// </summary>
		[YAXSerializeAs("SubtitleTracks")]
		public IEnumerable<SubtitleTrack> SubtitleTracks { get; set; }
	}
}
