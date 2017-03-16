package ar_ubin.benotified.tabs.messages;


import android.icu.text.DateFormat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ar_ubin.benotified.R;
import ar_ubin.benotified.data.models.Message;

public class MessagesViewHolder extends RecyclerView.ViewHolder
{
    public TextView vMessageTitle;
    public ImageButton vMessageMenu;
    public TextView vMessageDescription;
    public ImageView vMessageStatusIcon;
    public TextView vMessageTime;

    public MessagesViewHolder( View itemView ) {
        super( itemView );

        vMessageTitle = (TextView) itemView.findViewById( R.id.message_title );
        vMessageMenu = (ImageButton) itemView.findViewById( R.id.message_menu );
        vMessageDescription = (TextView) itemView.findViewById( R.id.message_description );
        vMessageStatusIcon = (ImageView) itemView.findViewById( R.id.message_status_ic );
        vMessageTime = (TextView) itemView.findViewById( R.id.message_status_time );
    }

    public void bindToMessage( Message message, View.OnClickListener menuClickListener ) {
        vMessageTitle.setText( message.getTitle() );
        vMessageDescription.setText( message.getDescription() );
        vMessageTime.setText( DateFormat.getTimeInstance( DateFormat.SHORT ).format( message.getTimestamp() ) );

        if( menuClickListener != null ) {
            vMessageMenu.setVisibility( View.VISIBLE );
            vMessageMenu.setOnClickListener( menuClickListener );
        }
    }

    public void bindToMessage( Message message ) {
        this.bindToMessage( message, null );
    }
}
