package dev.bread.application

import org.springframework.stereotype.Service

@Service
class ReviewReadService(
    private val reviewReader: ReviewReader,
    private val memberReader: MemberReader,
    private val menuReader: MenuReader
) {

    fun readOne(reviewId: Long, memberId: Long): ReadOneReviewCommand {
        val member = memberReader.read(memberId)
        val review = reviewReader.readByMemberId(memberId)
        val menus = menuReader.findRecommendMenuByMemberId(memberId)

        return ReadOneReviewCommand(
            userName = member?.name,
            reviewCount = review?.count(),
            menus = menus,
            averageRate = review?.map { it.rate() }?.average(),
            storeRate = review?.find { it.reviewId == reviewId }?.rate()
        )
    }
}
