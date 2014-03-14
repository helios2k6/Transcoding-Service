using System;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The VideoTrack XML node
	/// </summary>
	[YAXSerializeAs("VideoTrack")]
	[Serializable]
	public sealed class VideoTrack : Track
	{
		/// <summary>
		/// The Width XML node
		/// </summary>
		[YAXSerializeAs("Width")]
		public int Width { get; set; }

		/// <summary>
		/// The Height XML node
		/// </summary>
		[YAXSerializeAs("Height")]
		public int Height { get; set; }

		/// <summary>
		/// The DisplayAspectRatio XML node
		/// </summary>
		[YAXSerializeAs("DisplayAspectRatio")]
		public DisplayAspectRatio DisplayAspectRatio { get; set; }

		/// <summary>
		/// The FrameRate XML node
		/// </summary>
		[YAXSerializeAs("FrameRate")]
		public FrameRate FrameRate { get; set; }
	}
}