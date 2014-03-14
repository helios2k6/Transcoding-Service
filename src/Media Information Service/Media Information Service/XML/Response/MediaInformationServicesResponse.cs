using System;
using System.Collections.Generic;
using System.Security.Policy;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The Status XML node
	/// </summary>
	public enum Status
	{
		/// <summary>
		/// Represents a successful result
		/// </summary>
		[YAXEnum("Success")]
		Success,

		/// <summary>
		/// Represents a failure result
		/// </summary>
		[YAXEnum("Failure")]
		Failure,
	}

	/// <summary>
	/// Represents the root XML node
	/// </summary>
	[YAXSerializeAs("MediaInformationServicesResponse")]
	[Serializable]
	public sealed class MediaInformationServicesResponse
	{
		/// <summary>
		/// The Files XML node
		/// </summary>
		[YAXSerializeAs("Files")]
		public IEnumerable<File> Files { get; set; }
	}

	/// <summary>
	/// Represents a File XML node
	/// </summary>
	[YAXSerializeAs("File")]
	[Serializable]
	public sealed class File
	{
		/// <summary>
		/// Represents the Path XML node
		/// </summary>
		[YAXSerializeAs("Path")]
		public Url Path { get; set; }

		/// <summary>
		/// Represents the Status XML node
		/// </summary>
		[YAXSerializeAs("Status")]
		public Status Status { get; set; }

		/// <summary>
		/// Represents the MediaInfo XML node
		/// </summary>
		[YAXSerializeAs("MediaInfo")]
		public MediaInfo MediaInfo { get; set; }
	}
}
