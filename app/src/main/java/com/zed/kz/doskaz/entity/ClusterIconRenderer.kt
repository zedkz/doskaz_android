package com.zed.kz.doskaz.entity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import com.google.maps.android.ui.SquareTextView
import timber.log.Timber


class ClusterIconRenderer(
    private val context: Context?,
    private val googleMap: GoogleMap?,
    private val clusterManager: ClusterManager<Cluster>?
): DefaultClusterRenderer<Cluster>(context, googleMap, clusterManager){

    private var mIconGenerator: IconGenerator? = null
    private var mColoredCircleBackground: ShapeDrawable? = null
    private var mDensity = 0f

    init {
        mIconGenerator = IconGenerator(context)
        mColoredCircleBackground = ShapeDrawable(OvalShape())
        mDensity = context!!.resources.displayMetrics.density

        val squareTextView = SquareTextView(context)
        val layoutParams = ViewGroup.LayoutParams(-2, -2)
        squareTextView.layoutParams = layoutParams
        squareTextView.id = com.google.maps.android.R.id.amu_text
        val twelveDpi = (18.0f * mDensity).toInt()
        squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi)
        mIconGenerator?.setContentView(squareTextView)

        mIconGenerator?.setTextAppearance(com.google.maps.android.R.style.amu_ClusterIcon_TextAppearance)

        squareTextView.setTextColor(Color.BLACK)

        val outline = ShapeDrawable(OvalShape())
        outline.paint.color = Color.parseColor("#0F6BF5")
        val background = mColoredCircleBackground?.let { LayerDrawable(
            arrayOf<Drawable>(
                outline,
                it
            )
        ) }
        val strokeWidth = (mDensity * 4.0f).toInt()
        background?.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth)
        mIconGenerator?.setBackground(background)
    }

    override fun onBeforeClusterRendered(
        cluster: com.google.maps.android.clustering.Cluster<Cluster>,
        markerOptions: MarkerOptions
    ) {
        var totalCount = 0
        cluster.items?.forEach { totalCount += it.itemsCount ?: 0 }
        this.mColoredCircleBackground?.paint?.color = Color.WHITE
        val descriptor = BitmapDescriptorFactory.fromBitmap(
            this.mIconGenerator?.makeIcon(
                "${totalCount}"
            )
        )

        markerOptions.icon(descriptor)
    }

    override fun onBeforeClusterItemRendered(item: Cluster, markerOptions: MarkerOptions) {
        this.mColoredCircleBackground?.paint?.color = Color.WHITE
        val descriptor = BitmapDescriptorFactory.fromBitmap(
            this.mIconGenerator?.makeIcon(
                "${item.itemsCount}"
            )
        )

        markerOptions.icon(descriptor)
    }
}