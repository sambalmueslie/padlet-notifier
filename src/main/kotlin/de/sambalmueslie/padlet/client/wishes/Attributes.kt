package de.sambalmueslie.padlet.client.wishes


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Attributes(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("wall_id")
    val wallId: Long,
    @JsonProperty("published")
    val published: Boolean,
//    @JsonProperty("row_id")
//    val rowId: Any,
//    @JsonProperty("col_id")
//    val colId: Any,
    @JsonProperty("row_span")
    val rowSpan: Int,
    @JsonProperty("col_span")
    val colSpan: Int,
    @JsonProperty("is_content_hidden")
    val isContentHidden: Boolean,
    @JsonProperty("updated_at")
    val updatedAt: String,
    @JsonProperty("headline")
    val headline: String,
    @JsonProperty("subject")
    val subject: String,
    @JsonProperty("body")
    val body: String,
    @JsonProperty("attachment")
    val attachment: String,
    @JsonProperty("permalink")
    val permalink: String,
    @JsonProperty("author_id")
    val authorId: Long,
    @JsonProperty("created_at")
    val createdAt: String,
    @JsonProperty("content_updated_at")
    val contentUpdatedAt: String,
    @JsonProperty("color")
    val color: String?,
    @JsonProperty("location_point")
    val locationPoint: LocationPoint?,
    @JsonProperty("location_name")
    val locationName: String?,
//    @JsonProperty("top")
//    val top: Any,
//    @JsonProperty("left")
//    val left: Any,
    @JsonProperty("width")
    val width: Int,
//    @JsonProperty("position")
//    val position: Position? ,
    @JsonProperty("sort_index")
    val sortIndex: Long,
    @JsonProperty("wall_section_id")
    val wallSectionId: Long,
//    @JsonProperty("rank")
//    val rank: Any,
//    @JsonProperty("label")
//    val label: Any,
//    @JsonProperty("attachment_caption")
//    val attachmentCaption: Any,)
)
