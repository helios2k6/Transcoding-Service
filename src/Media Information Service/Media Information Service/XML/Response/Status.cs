using System.Runtime.Serialization;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The Status XML node
	/// </summary>
	[DataContract(Name = XmlConstants.Status, Namespace = XmlConstants.Namespace)]
	public enum Status
	{
		/// <summary>
		/// Represents a successful result
		/// </summary>
		[EnumMember]
		Success,

		/// <summary>
		/// Represents a failure result
		/// </summary>
		[EnumMember]
		Failure,
	}
}