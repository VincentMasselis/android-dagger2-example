package fr.vincentmasselis.daggersample.utils;

import android.support.annotation.NonNull;

import fr.vincentmasselis.daggersample.data.utils.StringParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Inspiré par RequestFuture de volley :
 * https://github.com/mcxiaoke/android-volley/blob/master/src/main/java/com/android/volley/toolbox/RequestFuture.java
 */
public class RequestFuture<T> implements Future<T>, Callback {
    private final Call mRequest;
    private final StringParser<T> mParser;

    private boolean mResultReceived = false;
    private Response mResult;
    private Exception mException;

    public static <T> RequestFuture<T> newFuture(Call request, StringParser<T> parser) {
        return new RequestFuture<>(request, parser);
    }

    private RequestFuture(Call request, StringParser<T> parser) {
        mRequest = request;
        mParser = parser;
        request.enqueue(this);
    }

    @Override
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (mRequest == null) {
            return false;
        }

        if (!isDone()) {
            mRequest.cancel();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public T get(long timeout, @NonNull TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(TimeUnit.MILLISECONDS.convert(timeout, unit));
    }

    private synchronized T doGet(Long timeoutMs) throws InterruptedException, ExecutionException, TimeoutException {
        if (mException != null) {
            throw new ExecutionException(mException);
        }

        if (mResultReceived) {
            try {
                return mParser.parse(mResult.body().string());
            } catch (JSONException | IOException e) {
                throw new ExecutionException(e);
            }
        }

        if (timeoutMs == null) {
            wait(0);
        } else if (timeoutMs > 0) {
            wait(timeoutMs);
        }

        if (mException != null) {
            throw new ExecutionException(mException);
        }

        if (!mResultReceived) {
            throw new TimeoutException();
        }

        try {
            return mParser.parse(mResult.body().string());
        } catch (JSONException | IOException e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public boolean isCancelled() {
        return mRequest != null && mRequest.isCanceled();
    }

    @Override
    public synchronized boolean isDone() {
        return mResultReceived || mException != null || isCancelled();
    }

    @Override
    public synchronized void onFailure(Request request, final IOException e) {
        mException = e;
        notifyAll();
    }

    @Override
    public synchronized void onResponse(final Response response) throws IOException {
        mResultReceived = true;
        mResult = response;
        notifyAll();
    }
}

