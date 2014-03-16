using System;
using System.Xml.Linq;
using YAXLib;

namespace MediaInformationService.XML.External.MediaInfo
{
	public sealed class TrackSerializer : ICustomSerializer<Track>
	{
		public Track DeserializeFromElement(XElement element)
		{
			var serializer = GetSerializer(element);
			return (Track)serializer.Deserialize(element);
		}

		private static YAXSerializer GetSerializer(XElement element)
		{
			XAttribute trackTypeAttribute = element.Attribute(XmlConstants.Type);

			if (trackTypeAttribute == null)
			{
				throw new Exception("Unable to find 'type' attribute for track deserialization");
			}

			string trackType = trackTypeAttribute.Value;
			switch (trackType)
			{
				case XmlConstants.General:
					return new YAXSerializer(typeof(GeneralTrack));
				case XmlConstants.Audio:
					return new YAXSerializer(typeof(AudioTrack));
				case XmlConstants.Video:
					return new YAXSerializer(typeof(VideoTrack));
				case XmlConstants.Text:
					return new YAXSerializer(typeof(SubtitleTrack));
				default:
					throw new Exception("Unable to detect track type. Track type was: " + trackType);
			}
		}

		#region unimplemented stuff
		public Track DeserializeFromAttribute(System.Xml.Linq.XAttribute attrib)
		{
			throw new NotImplementedException();
		}

		public Track DeserializeFromValue(string value)
		{
			throw new NotImplementedException();
		}

		public void SerializeToAttribute(Track objectToSerialize, System.Xml.Linq.XAttribute attrToFill)
		{
			throw new System.NotImplementedException();
		}

		public void SerializeToElement(Track objectToSerialize, System.Xml.Linq.XElement elemToFill)
		{
			throw new System.NotImplementedException();
		}

		public string SerializeToValue(Track objectToSerialize)
		{
			throw new System.NotImplementedException();
		}
		#endregion
	}
}
