package com.zea7ot.whorepresentsyou.util

class JsonTitle {
    companion object {
        const val NAME = "name"
        const val PARTY = "party"
        const val STATE = "state"
        const val DISTRICT = "district"
        const val PHONE = "phone"
        const val OFFICE = "office"
        const val LINK = "link"
        const val RESULTS = "results"

        // Google API
        const val ADDRESS_COMPONENTS = "address_components"
        const val LONG_NAME = "long_name"
        const val SHORT_NAME = "short_name"
        const val TYPES = "types"
        const val PLACE_ID = "place_id"
        const val FORMATTED_ADDRESS = "formatted_address"
    }
}

class JsonValue {
    companion object {
        const val POSTAL_CODE = "postal_code"
    }
}

class ApiUrl {
    class Member {
        companion object {
            const val BASE_URL = "https://whoismyrepresentative.com"
            const val GET_MEMBERS = "getall_mems.php"
        }
    }

    class GoogleApi {
        companion object {
            const val BASE_URL_MAP_GEOCODE = "https://maps.googleapis.com"
            const val GET_ADDRESSES = "maps/api/geocode/json"
        }
    }
}

class ApiQuery {
    companion object {
        const val ZIP = "zip"
        const val OUTPUT = "output"
        const val API_KEY = "key"
        const val LAT_LNG = "latlng"
        const val RESULT_TYPE = "result_type"
    }
}

class DefaultValue {
    class ApiQuery {
        companion object {
            const val OUTPUT = "json"
            const val RESULT_TYPE_POSTAL_CODE = "postal_code"
        }
    }

    class Invalid {
        companion object {
            const val POSTAL_CODE_STRING = "-1"
        }
    }
}
