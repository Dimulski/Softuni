﻿using SIS.HTTP.Enums;
using SIS.HTTP.Headers;
using SIS.HTTP.Responses;

namespace SIS.MvcFramework.Results
{
    class InlineResourceResult : HttpResponse
    {
        public InlineResourceResult(byte[] content, HttpResponseStatusCode responseStatusCode)
            : base(responseStatusCode)
        {
            Headers.AddHeader(new HttpHeader(HttpHeader.ContentLength, content.Length.ToString()));
            Headers.AddHeader(new HttpHeader(HttpHeader.ContentDisposition, "inline"));
            Content = content;
        }
    }
}
