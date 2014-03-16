using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Internal.Request
{
	/// <summary>
	/// Represents the File node of a Media Information Service Request
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.File, Namespace = XmlConstants.Namespace)]
	public sealed class File
	{
		/// <summary>
		/// Get or set the Path of the File XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Path, IsRequired = true)]
		public string Path { get; set; }
	}
}