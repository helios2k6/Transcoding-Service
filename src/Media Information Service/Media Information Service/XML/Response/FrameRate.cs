using System.Runtime.Serialization;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The FrameRate XML node
	/// </summary>
	[DataContract(Name = XmlConstants.FrameRate, Namespace = XmlConstants.Namespace)]
	public sealed class FrameRate
	{
		/// <summary>
		/// The Numerator XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Numerator, IsRequired = true)]
		public int Numerator { get; set; }

		/// <summary>
		/// The Denominator XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Denominator, IsRequired = true)]
		public int Denominator { get; set; }
	}
}