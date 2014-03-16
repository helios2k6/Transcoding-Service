using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Request
{
	/// <summary>
	/// Represents the MediaInformationServicesRequest XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.Request, Namespace = XmlConstants.Namespace)]
	public sealed class MediaInformationServicesRequest
	{
		/// <summary>
		/// The Files XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Files, IsRequired = true)]
		public File[] Files { get; set; }
	}
}