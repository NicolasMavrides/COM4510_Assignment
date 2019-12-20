package uk.ac.shef.oak.com4510.showTrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.ShowImageActivity;
import uk.ac.shef.oak.com4510.database.Photo;

/**
 * Gallery adapter class for the User Interface and Data Sources
 * related to the app's Gallery functions
 */

public class TripGalleryAdapter extends RecyclerView.Adapter<TripGalleryAdapter.View_Holder> {
    static private Context context;
    private static List<Photo> items;

    public TripGalleryAdapter(List<Photo> items) {
        this.items = items;
    }

    public void updateData(List<Photo> photos){
        Log.i("Updating: ", "doing");
        items.clear();
        items.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context= parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
        Log.i("Adding: ", "doing");
        if (holder!=null && items.get(position)!=null) {
            if (items.get(position).getFile_path()!=null){
                new UploadSingleImageTask().execute(new HolderAndPosition(position, holder));
                holder.name.setText(items.get(position).getTitle());
                holder.description.setText(items.get(position).getDescription());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImageActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
        //animate(holder);
    }

    public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView name;
        TextView description;

        View_Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
            name = itemView.findViewById(R.id.image_name);
            description = itemView.findViewById(R.id.image_description);
        }

    }

    private class UploadSingleImageTask extends AsyncTask<HolderAndPosition, Void,
                Bitmap> {
        HolderAndPosition holdAndPos;
        @Override
        protected Bitmap doInBackground(HolderAndPosition... holderAndPosition) {
            holdAndPos= holderAndPosition[0];
            Bitmap myBitmap =
                    decodeSampledBitmapFromResource(items.get(holdAndPos.position).getFile_path(), 100, 100);
            return myBitmap;
        }
        @Override
        protected void onPostExecute (Bitmap bitmap){
            holdAndPos.holder.imageView.setImageBitmap(bitmap);
        }
    }

    private class HolderAndPosition {
        int position;
        View_Holder holder;

        public HolderAndPosition(int position, View_Holder holder) {
            this.position = position;
            this.holder = holder;
        }
    }

    public static List<Photo> getItems() {
        return items;
    }

    public static void setItems(List<Photo> items) {
        TripGalleryAdapter.items = items;
    }
}