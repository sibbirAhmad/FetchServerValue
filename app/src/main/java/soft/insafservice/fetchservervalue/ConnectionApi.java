package soft.insafservice.fetchservervalue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionApi {
    String TAG ="[[[[ConnectionApi]]]] - - - > ";

    String server = null;
    Context context;

    public ConnectionApi(String serverUrl, Context ctx){
        this.server = serverUrl;
        this.context = ctx;


        //------------ todo : Executing..
        Async async = new Async();
        async.execute();

    }

    class Async extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL(server);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                Log.d(TAG, "doInBackground: ");
                return sb.toString().trim();
            } catch (Exception e) {
                //Log.d(TAG, " Error on doInBackground: "+server+"\n\n\n"+e);
                Log.d(TAG, " Error on doInBackground: \n\n\n"+e);
                return null;
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
               listener.onSuccess(s);
                Log.d(TAG, "onPostExecute: onSuccess"+s);

            }catch (Exception e){
                listener.onFailure(s);
                Log.d(TAG, "onPostExecute: Exception Fail : "+e);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            listener.onFailure("connectionProblem");
            Log.d(TAG, "onCancelled: "+s);
        }
    }

    private ConnectionListener listener;

    public void setConnectionListener(ConnectionListener listener)
    {
        this.listener=listener;
    }

    public interface ConnectionListener
    {
        public void onSuccess(String resultText);

        public void onFailure(String ErrorText);
    }

}
