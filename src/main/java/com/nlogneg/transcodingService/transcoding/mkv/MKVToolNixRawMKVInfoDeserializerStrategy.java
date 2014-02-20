package com.nlogneg.transcodingService.transcoding.mkv;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	private static final String AttachmentsHeader = "\\+\\S+Attachments";
	private static final String AttachedHeader = "\\+\\S+Attached";
	private static final String FileNameLabel = "\\+\\S+File name:\\S*";
	private static final String MimeLabel = "\\+\\S+Mime type:\\S*";
	private static final String FileUIDLabel = "\\+\\S+File UID:\\S*";

	/**
	 * Deserializes an MKVInfo from the given string, which is expected to be the 
	 * actual raw input, not the name of a file
	 */
	public Optional<MKVInfo> deserializeRawMKVInfo(String t){
		InputStream inputStream = new ByteArrayInputStream(t.getBytes());
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		List<Attachment> attachments = new ArrayList<Attachment>();
		
		//Close off the scanner when we're done
		try(Scanner scanner = new Scanner(reader);){
			if(scanner.hasNext(AttachmentsHeader)){
				//Get the start of the first line
				scanner.next(AttachmentsHeader);
				
				long attachmentId = 1;
				while(scanner.hasNext(AttachedHeader)){
					//Advance the pointer
					scanner.next(AttachedHeader);

					//Get the file name
					Optional<String> fileName = tryGetNextLineAfterToken(scanner, FileNameLabel);
					if(fileName.isNone()){
						//Can't do anything cause we don't know how the file name
						return Optional.none();
					}
					
					//Get MIME
					Optional<String> mime = tryGetNextLineAfterToken(scanner, MimeLabel);
					MimeType mimeType = null;
					if(mime.isSome()){
						try {
							mimeType = new MimeType(mime.getValue());
						}catch (MimeTypeParseException e){
							Log.error("Could not parse MIME type.", e);
						}
					}
					
					//File UID
					Optional<String> fileUID = tryGetNextLineAfterToken(scanner, FileUIDLabel);
					if(fileUID.isNone()){
						//If there's no UID, something went wrong
						Log.error("Could not get FileUID");
						return Optional.none();
					}
					
					long uid = -1;
					try{
						uid = Long.parseLong(fileUID.getValue());
					}catch(NumberFormatException e){
						Log.error("Could not parse FileUID");
						return Optional.none();
					}
					
					Attachment attachment = new Attachment(attachmentId, fileName.getValue(), mimeType, uid);
					attachments.add(attachment);
				}
			}
		}

		return Optional.make(new MKVInfo(attachments));
	}

	private static Optional<String> tryGetNextLineAfterToken(Scanner scanner, String token){
		if(scanner.hasNext(token)){
			scanner.next(token);
			if(scanner.hasNext()){
				Optional.make(scanner.next());
			}
		}

		return Optional.none();
	}
}
