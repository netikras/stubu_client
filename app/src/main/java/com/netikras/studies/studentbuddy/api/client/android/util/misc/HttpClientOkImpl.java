package com.netikras.studies.studentbuddy.api.client.android.util.misc;

import com.netikras.tools.common.exception.FriendlyUncheckedException;
import com.netikras.tools.common.remote.http.HttpClient;
import com.netikras.tools.common.remote.http.HttpRequest;
import com.netikras.tools.common.remote.http.HttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.3.
 */

public class HttpClientOkImpl extends HttpClient {

    @Override
    public HttpResponse sendRequest(HttpRequest request, HttpResponse expectedResponse) throws FriendlyUncheckedException {


        OkHttpClient conn = null;
        Request httpRequest = null;
        Response httpResponse = null;

        precheck(request);

        try {

            String urlString;
            String params;
            byte[] data = null;

            if (HttpRequest.HttpMethod.GET.equals(request.getMethod())) {
                urlString = request.buildUrlWithParams();
                params = null;
            } else {
                urlString = request.buildUrl();
                params = request.buildParams();
                data = request.getBytes();
            }

            if (data != null && params != null && !params.isEmpty()) {
                urlString = urlString + "?" + params;
            }

            String encoding = "UTF-8";

//            URL url = new URL(urlString);
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(urlString)
                    .method(request.getMethod().name(), new RequestBody() {
                        @Override
                        public MediaType contentType() {
                            List<String> types = request.getHeaders("Content-type");
                            if (!isNullOrEmpty(types)) {
                                return MediaType.parse(types.get(0));
                            }
                            return null;
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            if (request.getBytes() != null && request.getBytes().length > 0) {
                                sink.write(request.getBytes());
                            }
                        }
                    });


            Map<String, List<String>> headers = request.getHeaders();

            if (!isNullOrEmpty(request.getHeaders())) {
                for (Map.Entry<String, List<String>> headerEntry : headers.entrySet()) {
                    if (headerEntry.getValue() != null && !headerEntry.getValue().isEmpty()) {
                        String value = extractHeaderValue(headerEntry.getValue());
                        requestBuilder.addHeader(headerEntry.getKey().toLowerCase(), value);
                    }
                }
            }

            httpRequest = requestBuilder.build();

            conn = new OkHttpClient.Builder()
                    .connectTimeout(request.getConnectTimeout(), TimeUnit.SECONDS)
                    .readTimeout(request.getReadTimeout(), TimeUnit.SECONDS)
                    .build();

            long start = System.currentTimeMillis();
            httpResponse = conn.newCall(httpRequest).execute();

            if (expectedResponse != null) {
                expectedResponse.setRequestId(request.getRequestId());
                expectedResponse.setStatus(httpResponse.code());
                expectedResponse.setMessage(httpResponse.message());

                for (Map.Entry<String, List<String>> headerEntry : httpResponse.headers().toMultimap().entrySet()) {
                    if (headerEntry.getValue() != null && !headerEntry.getValue().isEmpty()) {
                        String value = extractHeaderValue(headerEntry.getValue());
                        expectedResponse.addHeader(headerEntry.getKey().toLowerCase(), value);
                    }
                }

                if (request.getReadTimeout() > 0) {
                    long end = System.currentTimeMillis();
                    expectedResponse.setMaxReadTimeout(request.getReadTimeout() - (end - start));
                }
                if (request.getMaxReceivedPayloadSize() > 0) {
                    expectedResponse.setMaxResponseSize(request.getMaxReceivedPayloadSize());
                }

                expectedResponse.digest(httpResponse.body().byteStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse != null) {
                    if (httpResponse.body() != null) {
                        httpResponse.body().close();
                    }
                }
            } catch (Throwable t) {
                System.err.println(t);
            }
        }

        return expectedResponse;
    }

    private void precheck(HttpRequest request) throws FriendlyUncheckedException {

    }

    private String extractHeaderValue(List<String> allValues) {
        String value = null;
        if (!allValues.isEmpty()) {
            if (allValues.size() > 1) {
                StringBuilder headersBuilder = new StringBuilder();
                for (int i = 0; i < allValues.size(); ) {
                    headersBuilder.append(allValues.get(i));
                    if (++i < allValues.size()) {
                        headersBuilder.append(",");
                    }
                }
                value = headersBuilder.toString();
            } else {
                value = allValues.get(0);
            }
        }

        return value;
    }


}
