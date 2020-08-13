package com.example.vsmtiinfo.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Adapter.StudijskiProgramiAdapter;
import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.Model.Predmet;
import com.example.vsmtiinfo.Model.Semestar;
import com.example.vsmtiinfo.Model.Studij;
import com.example.vsmtiinfo.Model.StudijData;
import com.example.vsmtiinfo.Model.StudijskiProgram;
import com.example.vsmtiinfo.Model.StudijskiProgrami;
import com.example.vsmtiinfo.Model.StudijskiSmjer;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForJson;

import java.util.ArrayList;


public class StudijskiProgramiFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyViewModel viewModel;
    private StudijskiProgrami studijskiProgrami;
    private static final String TAG = "MyApp";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
//        viewModel.Retrofit();
//        viewModel.SetOnStudijskiProgramiFinishListener(new WaitForJson() {
//            @Override
//            public void GetStudijskiProgrami(StudijskiProgrami SP) {
//                Log.d(TAG, "GetStudijskiProgrami:   " + SP.getStudijskiProgram());
//            }
//        });
//        studijskiProgrami = viewModel.LoadJsonStudijskiProgrami();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_studijski_programi, container, false);
//        LoadNews();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Dohvaćanje studijskih programa ..");
        progressDialog.show();
        viewModel.SetOnStudijskiProgramiFinishListener(new WaitForJson() {
            @Override
            public void GetStudijskiProgrami(StudijskiProgrami SP) {

                Log.d(TAG, "GetStudijskiProgrami: " + SP.getStudijskiProgram());
                recyclerView = viewGroup.findViewById(R.id.recyclerView);
                StudijskiProgramiAdapter studijskiProgramiAdapter = new StudijskiProgramiAdapter(getActivity(), SP.getStudijskiProgram());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(studijskiProgramiAdapter);
                progressDialog.hide();
            }
        });

        return viewGroup;
    }


}