using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Internal.Response
{
	/// <summary>
	/// The DisplayAspectRatio XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.DisplayAspectRatio, Namespace = XmlConstants.Namespace)]
	public sealed class DisplayAspectRatio
	{
		/// <summary>
		/// The Width XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Width, IsRequired = true)]
		public int Width { get; set; }

		/// <summary>
		/// The Height XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Height, IsRequired = true)]
		public int Height { get; set; }
	}
}