package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A deserializer for the output produced by the mkvinfo tool that's a part of the 
 * mkvtoolnix suite.
 * @author anjohnson
 *
 */
public class MKVToolNixRawMKVInfoDeserializerStrategy implements RawMKVInfoDeserializationStrategy<String>{

	private static final Logger Log = LogManager.getLogger(MKVToolNixRawMKVInfoDeserializerStrategy.class);

	private static final String AttachedHeader = "+ ATTACHED";
	private static final String FileNameLabel = "+ FILE NAME:";
	private static final String MimeLabel = "+ MIME TYPE:";
	private static final String FileUIDLabel = "+ FILE UID:";

	/**
	 * Deserializes an MKVInfo from the given string, which is expected to be the 
	 * actual raw input, not the name of a file
	 */
	public Optional<MKVInfo> deserializeRawMKVInfo(String rawMkvInfo){
		String[] splitString = rawMkvInfo.split("\\|");

		List<Attachment> attachments = new ArrayList<Attachment>();

		long attachmentId = -1;
		Optional<String> fileName = Optional.none();
		Optional<MimeType> mimeType = Optional.none();
		Optional<Long> fileUID = Optional.none();

		for(String item : splitString){
			String toUpper = item.toUpperCase().trim();

			//Find attachment header
			if(toUpper.contains(AttachedHeader)){
				//Increment counter and reset all references
				attachmentId++;
				fileName = Optional.none();
				mimeType = Optional.none();
				fileUID = Optional.none();
				continue;
			}

			//Found file name
			if(toUpper.contains(FileNameLabel)){
				fileName = tryParseDataFromTag(item);
			}

			//Found MIME type
			if(toUpper.contains(MimeLabel)){
				mimeType = tryParseMimeTypeFromTag(item);
			}

			if(toUpper.contains(FileUIDLabel)){
				fileUID = tryParseFileUID(item);
			}

			//Check to see if we can make a new Attachment
			if(fileName.isSome() && mimeType.isSome() && fileUID.isSome()){
				Attachment attachment = new Attachment(attachmentId, fileName.getValue(), mimeType.getValue(), fileUID.getValue());
				attachments.add(attachment);
				
				//Clear out these references
				fileName = Optional.none();
				mimeType = Optional.none();
				fileUID = Optional.none();
			}
		}

		if(attachments.size() > 0){
			return Optional.make(new MKVInfo(attachments));
		}
		
		return Optional.none();
	}

	private static Optional<Long> tryParseFileUID(String data){
		Optional<String> rawParsedString = tryParseDataFromTag(data);
		if(rawParsedString.isNone()){
			return Optional.none();
		}

		try{
			Long fileUID = Long.parseLong(rawParsedString.getValue());
			return Optional.make(fileUID);
		}catch(NumberFormatException e){
			Log.error("Could not parse File UID.", e);
			return Optional.none();
		}
	}

	private static Optional<MimeType> tryParseMimeTypeFromTag(String data){
		Optional<String> rawParsedString = tryParseDataFromTag(data);
		if(rawParsedString.isNone()){
			return Optional.none();
		}

		try {
			MimeType mimeType = new MimeType(rawParsedString.getValue());
			return Optional.make(mimeType);
		} catch (MimeTypeParseException e) {
			Log.error("Could not parse MIME type", e);
			return Optional.none();
		}
	}

	private static Optional<String> tryParseDataFromTag(String data){
		String[] splitData = data.split(":");
		if(splitData.length == 2){
			return Optional.make(splitData[1].trim());
		}
		return Optional.none();
	}
}
