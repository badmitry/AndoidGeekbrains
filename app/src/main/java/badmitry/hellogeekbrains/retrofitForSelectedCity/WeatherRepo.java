package badmitry.hellogeekbrains.retrofitForSelectedCity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepo {
    private static WeatherRepo instance = null;
    private APIWeatherForSelectedCity apiWeatherForSelectedCity;
    private APIForecastForSelectedCity apiForecastForSelectedCity;
    private APIForecastForCurrentCity apiForecastForCurrentCity;
    private APIWeatherForCurrentCity apiWeatherForCurrentCity;

    public static WeatherRepo getInstance() {
        if (instance == null) {
            instance = new WeatherRepo();
        }
        return instance;
    }

    private WeatherRepo() {
        apiWeatherForSelectedCity = createAdapterWeatherForSelectedCity();
        apiForecastForSelectedCity = createAdapterForecastForSelectedCity();
        apiForecastForCurrentCity = createAdapterForecastForCurrentCity();
        apiWeatherForCurrentCity = createAdapterWeatherForCurrentCity();
    }

    public APIWeatherForSelectedCity getApiWeatherForSelectedCity() {
        return apiWeatherForSelectedCity;
    }

    public APIForecastForSelectedCity getApiForecastForSelectedCity() {
        return apiForecastForSelectedCity;
    }

    public APIWeatherForCurrentCity getApiWeatherForCurrentCity() {
        return apiWeatherForCurrentCity;
    }

    public APIForecastForCurrentCity getApiForecastForCurrentCity() {
        return apiForecastForCurrentCity;
    }

    private APIForecastForSelectedCity createAdapterForecastForSelectedCity() {
        return buildRetrofit().create(APIForecastForSelectedCity.class);
    }

    private APIWeatherForSelectedCity createAdapterWeatherForSelectedCity() {
        return buildRetrofit().create(APIWeatherForSelectedCity.class);
    }

    private APIForecastForCurrentCity createAdapterForecastForCurrentCity() {
        return buildRetrofit().create(APIForecastForCurrentCity.class);
    }

    private APIWeatherForCurrentCity createAdapterWeatherForCurrentCity() {
        return buildRetrofit().create(APIWeatherForCurrentCity.class);
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Retrofit buildRetrofit() {
        OkHttpClient okHttpClient;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Log.d("!!!!!", "buildRetrofit: ");
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
        }else {
            Log.d("!!!!!", "buildRetrofit: 22222");
            okHttpClient = getUnsafeOkHttpClient()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
        }

        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
