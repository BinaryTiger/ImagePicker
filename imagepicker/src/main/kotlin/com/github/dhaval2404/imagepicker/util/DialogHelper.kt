package com.github.dhaval2404.imagepicker.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.R
import com.github.dhaval2404.imagepicker.constant.MediaProvider
import com.github.dhaval2404.imagepicker.listener.ResultListener
import kotlinx.android.synthetic.main.dialog_choose_app.view.*

/**
 * Show Dialog
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 04 January 2018
 */
internal object DialogHelper {

    /**
     * Show Image Provide Picker Dialog. This will streamline the code to pick/capture image
     *
     */
    fun showChooseAppDialog(context: Context, listener: ResultListener<MediaProvider>) {
        val layoutInflater = LayoutInflater.from(context)
        val customView = layoutInflater.inflate(R.layout.dialog_choose_app, null)

        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.title_choose_image_provider)
            .setView(customView)
            .setOnCancelListener {
                listener.onResult(null)
            }
            .setNegativeButton(R.string.action_cancel) { _, _ ->
                listener.onResult(null)
            }
            .show()

        // Handle Image Camera option click
        customView.image_camera_icon.setOnClickListener {
            listener.onResult(MediaProvider.CAMERA_IMAGE)
            dialog.dismiss()
        }

        // Handle Image Gallery option click
        customView.image_gallery_icon.setOnClickListener {
            listener.onResult(MediaProvider.GALLERY_IMAGE)
            dialog.dismiss()
        }

        // Handle Video Camera option click
        customView.video_camera_icon.setOnClickListener {
            listener.onResult(MediaProvider.CAMERA_VIDEO)
            dialog.dismiss()
        }

        // Handle Video Gallery option click
        customView.video_gallery_icon.setOnClickListener {
            listener.onResult(MediaProvider.GALLERY_VIDEO)
            dialog.dismiss()
        }
    }
}
