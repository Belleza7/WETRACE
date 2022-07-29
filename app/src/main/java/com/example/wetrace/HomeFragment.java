package com.example.wetrace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    int count = 0;
    private TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvcontent, tvheading, learnMoreTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tvCases = v.findViewById(R.id.totalCases);
        learnMoreTV = v.findViewById(R.id.learnMoreTV);
        tvRecovered = v.findViewById(R.id.recCases);
        tvCritical = v.findViewById(R.id.criticalCases);
        tvActive = v.findViewById(R.id.activeCases);
        tvTodayCases = v.findViewById(R.id.todayCases);
        tvTotalDeaths = v.findViewById(R.id.totalDeaths);
        tvTodayDeaths = v.findViewById(R.id.todayDeaths);
        tvcontent = v.findViewById(R.id.content);
        tvheading = v.findViewById(R.id.heading);

        learnMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public#:~:text=If%20COVID%2D19%20is%20spreading,where%20you%20live%20and%20work.";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        final String[] title = new String[]{"Keep your hands clean", "Avoid close contact", "Cover your own coughs and sneezes"};
        final String[] content = new String[]{"Regularly, wash your hands for at least 40 seconds with soap and warm water. This helps kill any germs that might be on your hands.", "Keep at least 1 metre distance from people who have respiratory symptoms, like coughing or sneezing. This helps avoid germs that might be in the air.", "Whether you feel unwell or not, make sure that you cover coughs or sneezes with a tissue, or with your elbow. This helps to stop your infection from spreading to others."};

        ImageView forward = v.findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == title.length) {
                    count = 0;
                } else {
                    tvcontent.setText(content[count]);
                    tvheading.setText(title[count]);
                    count += 1;
                }
            }
        });
        fetchdata();
        return v;
    }


    private void fetchdata() {
        String url = "https://corona.lmao.ninja/v2/all";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tvCases.setText((Integer.parseInt(jsonObject.getString("cases")) / 1000) + "k");
                    tvRecovered.setText((Integer.parseInt(jsonObject.getString("recovered")) / 1000) + "k");
                    tvCritical.setText((Integer.parseInt(jsonObject.getString("critical")) / 1000) + "k");
                    tvActive.setText((Integer.parseInt(jsonObject.getString("active")) / 1000) + "k");
                    tvTodayCases.setText((Integer.parseInt(jsonObject.getString("todayCases")) / 1000) + "k");
                    tvTotalDeaths.setText((Integer.parseInt(jsonObject.getString("deaths")) / 1000) + "k");
                    tvTodayDeaths.setText((Integer.parseInt(jsonObject.getString("todayDeaths")) / 1000) + "k");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}