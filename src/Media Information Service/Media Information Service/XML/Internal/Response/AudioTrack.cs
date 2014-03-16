using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Internal.Response
{
	/// <summary>
	/// The AudioTrack XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.AudioTrack, Namespace = XmlConstants.Namespace)]
	public sealed class AudioTrack : Track
	{
		/// <summary>
		/// The Channels XML node
		/// </summary>
		[DataMember(IsRequired = true, Name = XmlConstants.Channels)]
		public int Channels { get; set; }

		/// <summary>
		/// The Language XML node
		/// </summary>
		[DataMember(IsRequired = false, Name = XmlConstants.Language)]
		public string Language { get; set; }
	}
}