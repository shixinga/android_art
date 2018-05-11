package csx.haha.com.italker.frags.panel;

import android.view.View;


import java.util.List;

import csx.haha.com.common.widget.recycler.RecyclerAdapter;
import csx.haha.com.face.Face;
import csx.haha.com.italker.R;


/**
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected MyViewHolder<Face.Bean> myOnCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }

}
