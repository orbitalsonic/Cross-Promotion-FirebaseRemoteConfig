package com.orbitalsonic.crosspromotion.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.orbitalsonic.crosspromotion.utils.GeneralUtils.openPlayStoreApp
import com.orbitalsonic.crosspromotion.listeners.OnClickListeners.setSafeOnClickListener
import com.orbitalsonic.crosspromotion.viewmodel.PagerViewModel
import com.orbitalsonic.crosspromotion.R
import com.orbitalsonic.crosspromotion.utils.ScreenUtils.getScreenWidth
import com.orbitalsonic.crosspromotion.databinding.DialogPromotionBinding
import com.orbitalsonic.crosspromotion.databinding.FragmentPromotionHolderBinding
import com.orbitalsonic.crosspromotion.datamodel.PromotionModel

class PromotionPlaceholderFragment  : BaseFragment<FragmentPromotionHolderBinding>()  {

    private lateinit var pagerViewModel: PagerViewModel
    private var mPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerViewModel = ViewModelProvider(this).get(PagerViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState,
            R.layout.fragment_promotion_holder
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            onClickMethods()
        }


    }

    private fun onClickMethods(){
        binding.adCallToAction.setSafeOnClickListener {
            try {
                showPromotionDialog(promotionViewModel.promotionList[mPos])

            }catch (e:Exception){
                Log.e("CrossPromotionTag",e.message.toString())
            }
        }
    }

    private fun initViewModel() {

        pagerViewModel.mPosition.observe(viewLifecycleOwner) {
            try {
                if (promotionViewModel.promotionList.isNotEmpty()) {
                    mPos = it-1
                    binding.adHeadline.text =
                        promotionViewModel.promotionList[it - 1].title
                    binding.adBody.text =
                        promotionViewModel.promotionList[it - 1].short_desc
                    context?.let { mContext ->
                        Glide.with(mContext)
                            .load(promotionViewModel.promotionList[it - 1].icon)
                            .placeholder(R.drawable.bg_glide_white)
                            .into(binding.adAppIcon)
                    }

                }

            } catch (e: Exception) {
                      Log.e("CrossPromotionTag",e.message.toString())
            }
        }
    }

    private fun showPromotionDialog(promotionModel: PromotionModel) {
        activity?.let {
            val mDialog = Dialog(it)
            val dialogBinding: DialogPromotionBinding = DataBindingUtil.inflate(
                LayoutInflater.from(it), R.layout.dialog_promotion, null, false
            )
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog.setContentView(dialogBinding.root)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogBinding.dialogLayout.requestLayout()
            dialogBinding.dialogLayout.layoutParams.width = (it.getScreenWidth() * .90).toInt()
//        dialogBinding.dialogLayout.layoutParams.height =
//            (ScreenUtils.getScreenHeight(context as Activity) * .30).toInt()

            dialogBinding.tvAppName.text = promotionModel.title
            dialogBinding.tvAppDescription.text = promotionModel.short_desc

            Glide.with(it)
                .load(promotionModel.icon)
                .placeholder(R.drawable.bg_glide_white)
                .into(dialogBinding.ivAppLogo)

            Glide.with(it)
                .load(promotionModel.feature)
                .placeholder(R.drawable.bg_glide_white)
                .into(dialogBinding.ivFeatureImage)

            dialogBinding.btnCancel.setSafeOnClickListener {
                mDialog.dismiss()
            }

            dialogBinding.btnInstall.setSafeOnClickListener {
                activity.openPlayStoreApp(promotionModel.mPackage)
                mDialog.dismiss()
            }


            mDialog.show()

        }

    }


    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PromotionPlaceholderFragment {
            return PromotionPlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}