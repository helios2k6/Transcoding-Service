using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Internal.Response
{
	/// <summary>
	/// The SubtitleTrack XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.SubtitleTracks, Namespace = XmlConstants.SubtitleTrack)]
	public sealed class SubtitleTrack : Track
	{
		/// <summary>
		/// The Language XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Language, IsRequired = false)]
		public string Language { get; set; }
	}
}