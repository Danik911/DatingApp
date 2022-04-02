package com.example.datingapp.ui.profilescreen

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.datingapp.R
import com.example.datingapp.databinding.CustomDialogBinding
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.util.Constants.CAMERA_REQUEST_CODE
import com.example.datingapp.util.Constants.FILE_NAME
import com.example.datingapp.util.Constants.GALLERY_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var photoFile: File


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        binding.cameraImageView.setOnClickListener {
            showCustomDialog()
         }

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /* if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
             //val takenImage = data?.extras?.get("data") as Bitmap
             val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
             binding.profileImageView.setImageBitmap(takenImage)
         } else {
             super.onActivityResult(requestCode, resultCode, data)
         }*/
        /*  if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {

              // if multiple images are selected
              if (data?.getClipData() != null) {
                  var count = data.clipData?.itemCount

                  for (i in 0..count!!.minus(1)) {
                      var imageUri: Uri = data.clipData?.getItemAt(i)?.uri ?: Uri.EMPTY
                      //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                  }

              } else if (data?.getData() != null) {
                  // if single image is selected

                  var imageUri: Uri? = data.data
                  binding.profileImageView.setImageURI(imageUri)
              }
          }*/
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                    binding.profileImageView.setImageBitmap(takenImage)
                } else {
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    // if multiple images are selected
                    if (data?.getClipData() != null) {
                        var count = data.clipData?.itemCount

                        for (i in 0..count!!.minus(1)) {
                            var imageUri: Uri = data.clipData?.getItemAt(i)?.uri ?: Uri.EMPTY
                            //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                        }
                    } else if (data?.getData() != null) {
                        // if single image is selected

                        var imageUri: Uri? = data.data
                        binding.profileImageView.setImageURI(imageUri)
                    }
                }
            }

        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    private fun openGalleryToUploadImages() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun takePhoto() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(FILE_NAME)


        val fileProvider = FileProvider.getUriForFile(
            requireContext(),
            "com.example.datingapp.fileprovider",
            photoFile
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
    }

    private fun showCustomDialog() {
        val dialogBinding: CustomDialogBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(requireContext()),
                R.layout.custom_dialog,
                null,
                false
            )

        val customDialog = AlertDialog.Builder(requireContext(), 0).create()

        customDialog.apply {
            setView(dialogBinding?.root)
            setCancelable(true)
        }.show()

        dialogBinding?.btnCamera?.setOnClickListener {
            takePhoto()
        }
        dialogBinding?.btnGallery?.setOnClickListener {
            openGalleryToUploadImages()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

