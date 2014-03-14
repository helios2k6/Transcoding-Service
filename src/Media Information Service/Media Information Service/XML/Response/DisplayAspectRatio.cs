using System;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The DisplayAspectRatio XML node
	/// </summary>
	[Serializable]
	public sealed class DisplayAspectRatio
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

	}
}