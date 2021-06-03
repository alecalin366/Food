package com.example.food.Workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.food.Workout.VideoGlutesWorkout.VideoID;


public class RecyclerAdapterGlutesWorkout extends RecyclerView.Adapter<RecyclerAdapterGlutesWorkout.VideoInfoHolder> {


    public static final class VideoEntry {
        private final String text;

        private final String videoId;

        public VideoEntry(String text, String videoId) {
            this.text = text;
            this.videoId = videoId;
        }
    }
    public static List<VideoEntry> VIDEO_LIST;
    static {
        List< VideoEntry> list = new ArrayList<VideoEntry>();
        list.add(new VideoEntry("YouTube Collection", "Y_UmWdcTrrc"));
        list.add(new  VideoEntry("GMail Tap", "1KhZKNZO8mQ"));
        list.add(new VideoEntry("Chrome Multitask", "UiLSiqyDf4Y"));
        list.add(new VideoEntry("Google Fiber", "re0VRK6ouwI"));

        VIDEO_LIST = Collections.unmodifiableList(list);
    }


    private final Context ctx;


    public RecyclerAdapterGlutesWorkout(Context context) {
        this.ctx = context;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new VideoInfoHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {



////here
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                Log.d("onThumbnailError", "onThumbnailError: Fail");
            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);

            }
        };

        holder.youTubeThumbnailView.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(VideoID[position]);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);


            }



            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("onInitializationFailure", "onInitializationFailure: Fail");

            }
        });

        //end

//        holder.youTubeThumbnailView.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
//                youTubeThumbnailLoader.setVideo(VideoID[position]);
//                holder.videosTitleTextView.setText(Ttitles[position]);
//
//                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//                    @Override
//                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                        youTubeThumbnailLoader.release();
//
//                    }
//
//                    @Override
//                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//                        Toast.makeText(ctx, ""+errorReason, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText(ctx, ""+youTubeInitializationResult, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return VideoID.length;

    }
    public void ReleaseLoaders() {

//            for (YouTubeThumbnailLoader loader : loaders.values()) {
//                loader.release();
//            }

    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        LinearLayout ll = new LinearLayout(ctx);
        YouTubeThumbnailView youTubeThumbnailView;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton = itemView.findViewById(R.id.btnYoutube_player);

            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {

            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, DeveloperKey.DEVELOPER_KEY, VideoID[getLayoutPosition()]);
            ctx.startActivity(intent);
        }
    }
}