using System;
using System.Collections.Generic;
using YAXLib;

namespace MediaInformationService.XML.Request
{
	[YAXSerializeAs("MediaInformationSerivcesRequest")]
	[Serializable]
	public sealed class MediaInformationServicesRequest
	{
		[YAXSerializeAs("Files")]
		public IEnumerable<File> Files { get; set; }
	}
}