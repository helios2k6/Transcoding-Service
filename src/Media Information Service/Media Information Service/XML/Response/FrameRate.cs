using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The FrameRate XML node
	/// </summary>
	public sealed class FrameRate
	{
		/// <summary>
		/// The Numerator XML node
		/// </summary>
		[YAXSerializeAs("Numerator")]
		public int Numerator { get; set; }
		/// <summary>
		/// The Denominator XML node
		/// </summary>
		[YAXSerializeAs("Denominator")]
		public int Denominator { get; set; }
	}
}