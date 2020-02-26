package com.github.dhaval2404.imagepicker.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.github.dhaval2404.imagepicker.R
import com.github.dhaval2404.imagepicker.constant.MediaProvider
import java.io.File

/**
 * Get Gallery/Camera Intent
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 04 January 2018
 */
object IntentUtils {

    /**
     * @return Intent Gallery Intent
     */
    fun getGalleryIntent(context: Context, provider: MediaProvider): Intent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            var intent = getGalleryDocumentIntent(provider)
            if (intent.resolveActivity(context.packageManager) == null) {
                // No Activity found that can handle this intent.
                intent = getGalleryPickIntent(provider)
            }
            intent
        } else {
            getGalleryPickIntent(provider)
        }
    }

    /**
     * @return Intent Gallery Document Intent
     */
    private fun getGalleryDocumentIntent(provider: MediaProvider): Intent {
        // Show Document Intent
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)

        // Apply filter to show image only in intent
        intent.type = when (provider) {
            MediaProvider.GALLERY_VIDEO -> {
                "video/*"
            }
            MediaProvider.GALLERY_IMAGE -> {
                "image/*"
            }
            else -> {
                "*/*"
            }
        }

        return intent
    }

    /**
     * @return Intent Gallery Pick Intent
     */
    private fun getGalleryPickIntent(provider: MediaProvider): Intent {
        // Show Gallery Intent, Will open google photos
        val intent = Intent(Intent.ACTION_PICK)

        // Apply filter to show image only in intent
        intent.type = when (provider) {
            MediaProvider.GALLERY_VIDEO -> {
                "video/*"
            }
            MediaProvider.GALLERY_IMAGE -> {
                "image/*"
            }
            else -> {
                "*/*"
            }
        }

        return intent
    }

    /**
     * @return Intent ImageCamera Intent
     */
    fun getImageCameraIntent(context: Context, file: File): Intent? {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // authority = com.github.dhaval2404.imagepicker.provider
            val authority =
                context.packageName + context.getString(R.string.image_picker_provider_authority_suffix)
            val photoURI = FileProvider.getUriForFile(context, authority, file)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }

        return intent
    }

    /**
     * @return Intent VideoCamera Intent
     */
    fun getVideoCameraIntent(context: Context, file: File): Intent? {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // authority = com.github.dhaval2404.imagepicker.provider
            val authority =
                context.packageName + context.getString(R.string.image_picker_provider_authority_suffix)
            val videoURI = FileProvider.getUriForFile(context, authority, file)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }

        return intent
    }

    fun isCameraHardwareAvailable(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }
}
