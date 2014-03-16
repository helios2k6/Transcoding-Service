using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// Represents the root XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.Response, Namespace = XmlConstants.Namespace)]
	public sealed class MediaInformationServicesResponse
	{
		/// <summary>
		/// The Files XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Files, IsRequired = true)]
		public File[] Files { get; set; }
	}
}
