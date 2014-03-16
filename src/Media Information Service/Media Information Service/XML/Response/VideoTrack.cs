using System;
using System.Runtime.Serialization;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The VideoTrack XML node
	/// </summary>
	[Serializable]
	[DataContract(Name = XmlConstants.VideoTrack, Namespace = XmlConstants.Namespace)]
	public sealed class VideoTrack : Track
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

		/// <summary>
		/// The DisplayAspectRatio XML node
		/// </summary>
		[DataMember(Name = XmlConstants.DisplayAspectRatio, IsRequired = true)]
		public DisplayAspectRatio DisplayAspectRatio { get; set; }

		/// <summary>
		/// The FrameRate XML node
		/// </summary>
		[DataMember(Name = XmlConstants.FrameRate, IsRequired = true)]
		public FrameRate FrameRate { get; set; }
	}
}