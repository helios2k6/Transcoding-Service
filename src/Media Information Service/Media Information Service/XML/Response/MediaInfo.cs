using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The MediaInfo XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.MediaInfo, Namespace = XmlConstants.Namespace)]
	public sealed class MediaInfo
	{
		/// <summary>
		/// The VideoTracks XML node
		/// </summary>
		[DataMember(Name = XmlConstants.VideoTracks, IsRequired = true)]
		public VideoTrack[] VideoTracks { get; set; }

		/// <summary>
		/// The AudioTracks XML node
		/// </summary>
		[DataMember(Name = XmlConstants.AudioTracks, IsRequired = true)]
		public AudioTrack[] AudioTracks { get; set; }

		/// <summary>
		/// The SubtitleTracks XML node
		/// </summary>
		[DataMember(Name = XmlConstants.SubtitleTracks, IsRequired = true)]
		public SubtitleTrack[] SubtitleTracks { get; set; }
	}
}
