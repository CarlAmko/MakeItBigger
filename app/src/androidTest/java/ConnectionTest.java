import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.jokeproviderlibrary.EndpointAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

/**
 * Created by Carl on 5/4/2017.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ConnectionTest {

    @Test
    public void checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) InstrumentationRegistry.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        assertThat(networkInfo != null && networkInfo.isConnectedOrConnecting(), is(true));
    }

    // Note: Test is configured to check for local server.
    // As such, the test will pass on an emulator, but will fail on a physical device.
    @Test
    public void checkServerConnection() {
        final CountDownLatch sync = new CountDownLatch(1);
        final StringBuilder response = new StringBuilder();

        EndpointAsyncTask task = new EndpointAsyncTask();
        task.addListener(new EndpointAsyncTask.EndpointResponseListener() {
            @Override
            public void onJokeEndpointResponse(String res) {
                response.append(res);
                sync.countDown();
            }
        });
        task.execute();

        try {
            // Wait for up to 5 seconds.
            sync.await(5, TimeUnit.SECONDS);

            String responseString = response.toString();

            // Check that response isn't empty.
            assertThat(responseString, not(isEmptyOrNullString()));

            // Check that response isn't an error.
            assertThat(responseString, not("could not connect"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
