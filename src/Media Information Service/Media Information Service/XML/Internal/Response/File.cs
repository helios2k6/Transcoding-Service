using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Internal.Response
{
	/// <summary>
	/// Represents a File XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.File, Namespace = XmlConstants.Namespace)]
	public sealed class File
	{
		/// <summary>
		/// Represents the Path XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Path, IsRequired = true)]
		public string Path { get; set; }

		/// <summary>
		/// Represents the Status XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Status, IsRequired = true)]
		public Status Status { get; set; }

		/// <summary>
		/// Represents the MediaInfo XML node
		/// </summary>
		[DataMember(Name = XmlConstants.MediaInfo, IsRequired = true)]
		public MediaInfo MediaInfo { get; set; }
	}
}