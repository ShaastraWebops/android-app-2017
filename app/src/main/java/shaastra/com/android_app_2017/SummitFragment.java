package shaastra.com.android_app_2017;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummitFragment extends Fragment {
    ImageButton summit_events, summit_speakers, summit_reg, summit_partners, summit_prev, summit_faq;


    public SummitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        summit_events = (ImageButton) view.findViewById(R.id.summ_events);
        summit_speakers = (ImageButton) view.findViewById(R.id.summ_speakers);
        summit_faq = (ImageButton) view.findViewById(R.id.summ_faq);
        summit_partners = (ImageButton) view.findViewById(R.id.summ_partners);
        summit_reg = (ImageButton) view.findViewById(R.id.summ_registration);
        summit_prev = (ImageButton) view.findViewById(R.id.summ_past_versions);
        summit_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SummitEvents.class));
            }
        });
        summit_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SummitPast.class));
            }
        });
        summit_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SummitRegistration.class));
            }
        });
        summit_partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SummitPartners.class));
            }
        });
        summit_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SummitFAQ.class));
            }
        });
        summit_speakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SummitSpeakers.class));
            }
        });







    }
}
