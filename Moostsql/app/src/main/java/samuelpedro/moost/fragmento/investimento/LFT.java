package samuelpedro.moost.fragmento.investimento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samuel on 8/27/17.
 */
public class LFT extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(samuelpedro.moost.R.layout.lft, container, false);
        return view;
    }

}
