package com.numq.mvarchitecture.constant

object AppConstants {

    object Api {
        object Image {
            const val URL = "https://picsum.photos"
            const val RANDOM_IMAGE = "/{width}/{height}"
            const val IMAGE_DETAILS = "/id/{id}/info"
        }
    }

    object Database {
        const val NAME = "app_database"

        object Images {
            const val TABLE_NAME = "favorite_images"
        }
    }

    object Paging {
        const val DEFAULT_SIZE = 15
    }

}