package com.xlteam.socialcaption.external.datasource;

import android.content.Context;

import com.xlteam.socialcaption.external.utility.PrefUtils;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.ArrayList;
import java.util.List;

import static com.xlteam.socialcaption.external.utility.Constant.TYPE_CUOC_SONG;
import static com.xlteam.socialcaption.external.utility.Constant.TYPE_THA_THINH;
import static com.xlteam.socialcaption.external.utility.Constant.TYPE_BAN_BE;

public class CaptionDataSource {
    private static final String TAG = "CaptionDataSource";

    private CaptionDataSource() {
    }

    private static class Holder {
        public static CaptionDataSource INSTANCE = new CaptionDataSource();
    }

    public static CaptionDataSource getInstance() {
        return Holder.INSTANCE;
    }

    public List<CommonCaption> getDataLocal(Context context) {
        int currentVersion = PrefUtils.getVersionLocalDatabase(context); // default là 0
        List<CommonCaption> listData = new ArrayList<>();
        if (currentVersion < 1) {
            listData.addAll(getDataFirstTime());
            PrefUtils.setVersionLocalDatabase(context, 1);
        }
//        else if (version < 2) {
//            listData.addAll(getDataSecondTime());
//        }
//        else if (version < 3) {
//
//        }
        return listData;
    }

    public List<CommonCaption> getDataFirstTime() {
        List<CommonCaption> listData = new ArrayList<>();

        /**
         * ĐỨC: START
         */

        listData.add(new CommonCaption("Đồ rê mi pha son la si mê Anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Trời thu đẹp nhất về đêm\nĐời em đẹp nhất là thêm anh vào", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Chỉ cần anh muốn hẹn hò\nNgười xinh, cảnh đẹp, cuộc tình em lo", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đừng gọi em là thiếu nữ\ntrong khi thứ em thiếu là đàn ông", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đêm nay ánh trăng mập mờ\nGiường anh có rộng em ngủ nhờ một đêm...", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Gặp anh em bỗng muốn thờ\nKhông thờ ích thích mà thờ ương thương", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thích em thì hôn má, còn mà thích quá thì mình hôn môi", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Sáng anh nhớ tôi thì tối anh nhớ sang", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Sống ảo thì em có, nhưng sống cùng ai đó thì em chưa", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh mặc gì cũng được nhưng không được mặc kệ em", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Tóc em màu đen, môi em màu đỏ\nLại gần em hỏi nhỏ, anh có nhớ em không?", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em vốn dĩ yêu bản thân mình\nNhưng giờ lại muốn ngoại tình với anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Trời sinh yếu đuối mỏng manh\nNên cần được tựa vai anh mỗi ngày", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Cảm lạnh là do gió, còn cảm nắng chắc chắn là do anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh xem em đã đủ ngầu\nĐể anh quên mối tình đầu hay chưa?", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đêm Hà Nội sương mù bao phủ\nNhớ anh nhiều em có ngủ được đâu", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thanh xuân như một chén trà\nQuanh năm uống rượu nên trà còn nguyên", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Muốn tìm mật ngọt trên đời\nThì mình phải tập tin lời đàn ông", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Tính em rất tồi\nNhưng tối em rất tình", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Sống ảo thì em có\nNhưng sống cùng ai đó thì em chưa", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Gọi anh là mì gói, vì em muốn ăn liền", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Trời xanh gió mát chẳng ngừng\nAnh không cẩn thận coi chừng iu em", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em vụng về làm gì cũng đổ\nTấm thân này cũng lỡ đổ vào anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đừng như Hà Lan và Ngạn\nVì em không muốn chúng ta là bạn", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thời tiết trái gió dở trời\nCần người trái giới dở trò", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em không có ba vòng căng đét\nNhưng nhan sắc này đủ tạo nét chưa anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đó giờ chỉ toàn ăn cơm với cá\nEm làm gì biết thơm má ai đâu", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh thích con gái dễ thương hay quyến rũ, hỏi thế cho vui chứ em đây có đủ", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em dửng dưng trước vạn người là thế, nhưng lại yếu đuối trước sự tử tế của anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Bình minh thì ở phía Đông, bình yên thì ở phía em đây nè", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Nếu sốt cao thì gọi cấp cứu, nếu nhớ em nhiều, chờ xíu em qua", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Có những ngày mệt chẳng muốn làm gì cả, chỉ muốn làm người yêu anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Lồng thì em để nhốt chim\nCòn anh em nhốt trong tim em này", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Bệnh corona thì tìm bác sĩ\nBệnh codonqua thì tìm đến em.", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thân thiện với môi trường\nTừ trường với môi anh.", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Ngày xử em thích màu xanh dương\nNgày nay em thích anh người em thương", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Bột ngọt thì ngọt nhưng gây ung thư\nCòn anh cũng ngọt nhưng gây tương tư", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Không mơ cổ tích hoang đường\nChỉ mơ giấc mộng đời thường có anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em đã bị nhiễm một căn bệnh gọi là yêu, và em tin rằng mình đã mắc nó từ anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em có một siêu năng lực là nhắm mắt lại là có thể nhìn thấy anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em yêu bầu trời xanh, yêu cả cánh hoa hồng. Nhưng không quên một thứ, đó chính là yêu anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Romeo thì có Juliet, Jack thì có Rose, còn anh thì có em", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Tính mời anh đi ăn tối nhưng lại sợ thành bữa tối của anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thay vì tặng anh một đóa hồng không héo, để em gửi anh một mối tình không phai", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh có biết cơ thể anh được tạo thành từ 70% nước không? Và em cảm thấy rất khát ngay bây giờ", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thức khuya em tỉnh bằng trà, yêu anh em trả bằng tình được không?", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em đoán tên anh là Google. Bởi vì anh đang có mọi thứ mà em đang tìm kiếm", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em nghĩ điện thoại của em gặp vấn đề rồi, nó chưa có số điện thoại của anhh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Hà Nội không thể vì hoàng hôn mà trở nên rực rỡ. Nhưng em có thể vì anh mà trở nên yêu đời", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh ở ngoài tầm mắt của em, nhưng luôn ở trong tâm trí của em.", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em ước anh là tấm gương của em, để em có thể nhìn anh vào mỗi sáng", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Nếu mỗi lần nghĩ về anh em được một thanh kẹo, chắc em sẽ béo lên nhanh chóng mất", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em từng nghĩ nơi hạnh phúc nhất là Thiên Đường, nhưng đó là trước khi em ở bên anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Hoa hồng màu đỏ, Violet thì màu xanh, còn em thì yêu anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Mặt trời vừa mọc hay anh chỉ vừa cười với em?", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Mọi người thường nói không có gì là vĩnh cửu, nhưng đó là trước khi tình yêu của chúng mình xuất hiện", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em luôn có nhiều con đường để đi, nhưng em chọn đi theo lối nhỏ dẫn đến anhh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Chơi trò tung đồng xu đi anh, nếu mặt ngửa anh phải yêu em, nếu mặt sấp, em sẽ bị anh yêu", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Nếu anh là người vô gia cư, hãy chuyển vào sống trong tim em này, miễn phí đấy", null, TYPE_THA_THINH, false));

        //cuộc sống
        listData.add(new CommonCaption("Thế giới bạn không bước vào được thì đừng cố chen vào, làm khó người khác, lỡ dở mình, hà tất chứ?", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đôi khi, không cẩn thận biết một số chuyện, mới phát hiện ra rằng những điều bản thân để tâm lại nực cười đến thế.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng bao giờ thay đổi mình vì người khác. Nếu họ không thể tiếp nhận một con người nhiều điểm xấu là bạn, thì cũng không xứng để có được một con người với nhiều điểm tốt là bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Bạn cần sức mạnh, nghị lực nên cuộc sống đã đặt ra những khó khăn nghịch cảnh để bạn vượt qua và trở nên mạnh mẽ hơn", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống của bạn chỉ thật sự ý nghĩa và trọn vẹn khi bạn biết giữ gìn và nuôi dưỡng ước mơ, biết ghi nhận, biết tin vào những lời hứa.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng bao giờ cau mày hay nhăn mặt thậm chí khi bạn đang buồn, chắc chắn sẽ có ai đó yêu bạn chỉ vì nụ cười của bạn thôi.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Với thế giới bạn chỉ là một cá nhân nhưng đối với một ai đó, bạn là cả thế giới.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Bạn cần sự hiểu biết và sáng tạo nên cuộc sống đã ban cho bạn đôi bàn tay và trí óc để khám phá và làm việc.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Nếu để ý đến những điều bạn đang có trong cuộc sống, bạn sẽ nhận được nhiều hơn thế. Còn nếu chỉ để ý đến những điều bạn không có, bạn sẽ thấy mình không bao giờ có đủ.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Bạn sẽ tìm thấy niềm vui khi giúp đỡ người khác bằng tất cả tấm lòng.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy cảm ơn những lúc bạn gặp khó khăn, bởi nếu không có khó khăn, bạn sẽ không có cơ hội để hiểu mình và trải nghiệm cuộc sống.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Lòng tin cũng giống như một tờ giấy, khi đã nhàu nát sẽ không bao giờ phẳng phiu được.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống có quyền đẩy bạn ngã nhưng ngồi đó than khóc hay đứng dậy và tiếp tục là quyền của bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Chẳng có gì trở nên dễ dàng hơn. Chỉ là bạn trở nên mạnh mẽ hơn mà thôi.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Ta không thể bắt đầu lại nhưng ta có thể mở đầu bây giờ và làm nên một kết thúc mới.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy giữ khuôn mặt bạn luôn hướng về ánh mặt trời, và bóng tối sẽ ngả phía sau bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống không phải là phim ảnh, không có nhiều đến thế... những lần không hẹn mà gặp.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Muốn đổi thói quen, phải thay hành động.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Sống ở đời chẳng ai muốn mình trở thành người xấu, ai cũng muốn một cuộc sống ngẩng cao đầu không hổ với người, không thẹn với lòng. Thế nhưng sự đời đôi khi chẳng như ý muốn, đôi khi người ta phải sống hai mặt để đổi lấy hai chữ bình yên.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy luôn nhớ câu nói \"không có gì là tuyệt đối\". Đừng buồn khi bạn thất bại, sai lầm nhất của con người là không mắc sai lầm.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng cố kiếm nhiều tiền, ước mơ cao sang, cố gắng khiến người khác biết bạn giàu có. Hãy cố gắng làm cho cuộc sống của bạn ý nghĩa hơn. Vì bạn không thể mang theo gì khi chết đâu.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Khi cuộc sống quá phức tạp, hãy làm cho nó đơn giản đi. Hành động tốt nhiều hơn là nói miệng.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng quyết định việc gì khi bạn đang xúc động dù vui hay đang buồn. Nó sẽ khiến cuộc sống của bạn gặp rắc rối.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Trong cuộc sống hiện đại: Nụ cười giúp bạn giải quyết nhiều vấn đề còn im lặng giúp bạn tránh xa vô số vấn đề rắc rối.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống luôn bất công, ngừng than phiền. Hãy tạo ra giá trị riêng cho bản thân bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Lo lắng sẽ cướp đi thời gian và niềm vui của bạn. Bớt suy nghĩ khi nó không có thay đổi giúp ích cho bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Không tranh cãi với kẻ ngu, nếu không sẽ không biết ai là kẻ ngu thật sự.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Lúc nào tôi cũng bay theo hướng bay của người khác. Lần này tôi sẽ bay theo con đường mà tôi đã lựa chọn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Có những lúc, không có lần sau, không có cơ hội bắt đầu lại. Có những lúc, bỏ lỡ hiện tại, vĩnh viễn không còn cơ hội nữa.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy dùng thái độ can tâm tình nguyện để sống một cuộc sống an ổn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống cũng tương tự như một trò đấm bốc. Thất bại không được tuyên bố khi bạn ngã xuống mà là khi bạn từ chối \"đứng dậy\".", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hai nơi giá trị nhất trên thế giới: Nơi đẹp nhất trong suy nghĩ của một người và nơi an toàn nhất trong lời cầu nguyện của một người.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Nếu giọt nước rơi xuống hồ, nó sẽ biến mất. Nhưng nếu rơi xuống lá sen, nó sẽ tỏa sáng như một viên ngọc. Rơi giống nhau nhưng ở cùng ai mới là điều quan trọng.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Có sinh sẽ có tử, song chỉ cần bạn vẫn đang có mặt trên đời này, thì phải sống bằng cách tốt nhất. Có thể không có tình yêu, không có đồ hàng hiệu, song không thể không vui vẻ.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Họ tốt đến đâu không quan trọng, bởi những thứ đó thuộc về họ. Họ tốt với bạn thế nào mới quan trọng, bởi những thứ đó thuộc về bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Thứ không cần, có tốt đến đâu cũng là rác.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Nếu bạn không mù, thì đừng dùng tai để hiểu tôi.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Sự lợi hại thực sự không phải là bạn quen biết bao nhiêu người, mà là vào lúc bạn gặp hoạn nạn, có bao nhiêu người quen biết bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Trong cuộc sống đừng chờ đợi sự may mắn, mà hãy thực hiện và cũng đừng sợ sự thất bại, nếu bạn sợ, bạn sẽ chẳng làm được việc gì nên hồn đâu.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Để cuộc sống tươi đẹp hơn, hãy dậy thật sớm mỗi ngày, để tận hưởng ánh nắng chan hòa của buổi sáng mai.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cười nhiều lên một chút, với bạn bè, người thân, với những người mỉm cười với ta, và cả những người ta tình cờ gặp mặt, dù chẳng thân nhiều.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy rộng lòng thêm một chút, mạnh dạn bày tỏ tình cảm với mọi người, và đón nhận những tình cảm họ dành cho ta, để biết \"cảm giác bình thường tuyệt vời\" của tình yêu thương, để sống chan hòa và cởi mở.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Tinh tế hơn một chút. Dịu dàng hơn một chút. Mạnh mẽ hơn một chút. Chú ý đến bản thân hơn một chút. Người lớn hơn một chút. Tin tưởng hơn một chút. Dứt khoát hơn một chút. Thay đổi… một chút thôi mà! Để cuộc sống tươi đẹp hơn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Tôi tự nhủ với bản thân mình rằng, cần phải sống chân thực. Bất kể người khác nhìn mình bằng con mắt nào đi chăng nữa, dù cả thế giới phủ định, tôi vẫn có bản thân tin tưởng mình.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng bao giờ thay đổi mình vì người khác. Nếu họ không thể tiếp nhận một con người nhiều điểm xấu là bạn, thì cũng không xứng để có được một con người với nhiều điểm tốt là bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Chúng ta không nên bỏ cuộc, chúng ta không nên đề những khó khăn đánh bại mình.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng nói mà hãy làm. Đừng huyên thuyên mà hãy hành động. Đừng hứa mà hãy chứng minh những câu nói hay về cuộc sống tươi đẹp. Đừng bao giờ quyết định những vấn đề lâu dài trong lúc cảm xúc đang ngắn hạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đối với bạn mà nói, sẽ chẳng bao giờ là quá già để có một mục tiêu mới hay để mơ một giấc mơ mới.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống chỉ mang lại cho chúng ta 10% cơ hội, 90% còn lại là do chúng ta trải nghiệm thế nào với nó.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Trong trò chơi của cuộc sống, trước khi bạn nhận được bất cứ cái gì, bạn phải cho đi một thứ gì đó!.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng khóc tiếc nuối cho những gì đã xảy ra trong quá khứ. Đừng căng thẳng cho những việc chưa xảy ra trong tương lai. Hãy sống trọn vẹn ở thời điểm hiện tại và làm nó thật tươi đẹp.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng che dấu tình yêu và sự dịu dàng của mình cho đến khi bạn lìa đời. Hãy làm cuộc đời bạn tràn đầy sự ngọt ngào. Hãy nói những lời thân thương khi bạn còn nghe được và tim bạn còn rung động.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Sự chân thành là điều tốt đẹp nhất bạn có thể đem trao tặng một người. Sự thật, lòng tin cậy, tình bạn và tình yêu đều tùy thuộc vào điều đó cả.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy can đảm để từ bỏ, từ bỏ những thứ không thuộc về mình, từ bỏ những thứ chẳng phải là của mình, đã là của mình thì sẽ là của mình, còn đã là của người khác thì cho dù bạn có giành giật, nó cũng không thuộc về bạn", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy đủ bản lĩnh để thực hiện những ước muốn trong cuộc sống, hãy thực hiện những ước muốn nhỏ bé trước, thì khi đó những điều lớn lao là vô cùng dễ dàng.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy đủ khát khao để vươn tới, đừng nản lòng hay bỏ cuộc khi thực hiện một việc gì đó, hãy thực hiện nó bằng lòng quyết tâm của bạn, cho dù nó thất bại thì những điều bạn nhận được từ những thất bại đó cũng vô cùng lớn lao.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Thất bại đơn giản chỉ là cơ hội để bắt đầu lại mọi thứ một cách thông minh hơn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống của bạn chỉ thật sự ý nghĩa và trọn vẹn khi bạn biết giữ gìn và nuôi dưỡng ước mơ, biết ghi nhận, biết tin vào những lời hứa.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng bao giờ nói bạn đã thất bại cho đến khi đó là nỗ lực cuối cùng của bạn. Và đừng bao giờ nói rằng đó là nỗ lực cuối cùng của bạn cho đến khi bạn đã thành công.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Khó khăn không trường tồn, chỉ có con người cứng rắn trường tồn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Người duy nhất mắc sai lầm chính là người không làm gì cả. Đừng sợ sai lầm, miễn là bạn đừng mắc cùng một sai lầm hai lầm.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Khi cuộc đời đẩy bạn ngã, hãy cố hạ cánh bằng lưng. Bởi vì nếu bạn có thể nhìn lên, bạn có thể đứng dậy. Hãy để lý trí kéo bạn dậy!", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Con tàu rất an toàn khi neo đậu ở cảng, nhưng người ta đóng tàu không phải vì mục đích đó.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Mạnh mẽ lên vì mọi thứ sẽ tốt đẹp. Có thể bây giờ là bão tố, nhưng trời đâu thể mưa mãi được.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Những khó khăn trong cuộc sống là điều không tránh khỏi, việc cần làm là lựa chọn cách thức để vượt qua.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Trong cuộc đời này có một số chuyện nhất định phải tự mình giải quyết, dù đêm tối đến đâu, đường xa đến mấy thì vẫn cứ phải một mình kiên cường tiến lên phía trước.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống rất ngắn, đừng lãng phí nó bởi nỗi buồn. Hãy là chính mình, luôn vui vẻ, tự do và trở thành bất cứ gì bạn muốn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Tập hài lòng với thứ mình đang có, học cách từ bỏ với thứ trời không cho.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Tôi không bao giờ hối hận với những gì mình đã làm. Tôi chỉ hối hận với những việc mình đã không làm khi có cơ hội.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Sự cô đơn thôi thúc bạn trưởng thành, những khi trưởng thành bạn mới hiểu rằng nhiều khi cô đơn cũng chính là bình yên.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy yêu bản thân bạn vì chỉ có \"nó\" là không bao giờ phản bội bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Học cách sống tức là học cách tự do, và tự do tức là chấp nhận việc gì phải đến sẽ đến!", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống cũng giống như đi xe đạp, nếu muốn thăng bằng bạn phải tiếp tục di chuyển.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Trong cuộc sống, không có ai là người tốt và chân chính cả. Khi là người hùng của một nước thì chính bạn cũng là kẻ thù của quốc gia còn lại.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Thái độ sống của bạn sẽ quyết định là bạn có trưởng thành hơn hay mãi mãi không thể trưởng thành được, cho dù bạn có già đi.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Không gì có thể ngăn cản được người có thái độ đúng đắn vươn tới mục tiêu của mình; và cũng không có gì trên thế gian này có thể giúp được một người có thái độ sống sai lầm.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("10% cuộc sống của bạn là do những gì bạn tạo ra, còn 90% còn lại tùy thuộc vào cách bạn suy nghĩ và cảm nhận.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Lo lắng chính là điều vô bổ nhất trên đời. Nó giống như việc bạn cầm một chiếc ô và chạy vòng vòng đợi trời mưa xuống.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống không có lạc thú nào hơn ngoài vượt qua gian khó, bước từng bước tiến tới thành công, lập nên những ước vọng mới và chứng kiến chúng được thỏa mãn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng sợ thất bại mà hãy sợ rằng bạn sẽ không cố gắng hết sức.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống có cách riêng của nó khiến mọi thứ cuối cùng đều trở nên tốt đẹp.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Khi cuộc sống đẩy bạn vào khó khăn, hãy đừng hỏi: \"tại sao lại là tôi\" mà hãy mỉm cười", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Nếu đã là con đường của bạn, bạn phải tự mình bước đi, người khác có thể đi cùng nhưng không ai bước hộ bạn.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng hủy hoại ngày hôm nay bằng một suy nghĩ tiêu cực của ngày hôm qua. Ngày hôm qua nó qua rồi.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Không bao giờ bỏ cuộc cho dù bạn ở trong hoàn cảnh nào đi nữa. Hãy đối diện với sự thật.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đôi lúc bạn đối mặt với khó khăn không phải vì bạn làm điều gì đó sai mà bởi vì bạn đang đi đúng hướng.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Những điều tốt đẹp đến với ai tin tưởng, những điều tốt hơn đến với ai kiên nhẫn và... những điều tốt nhất chỉ đến với người không bỏ cuộc.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc hành trình ngàn dặm... phải bắt đầu từ những bước đầu tiên.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng mơ trong cuộc sống... mà hãy sống cho giấc mơ.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Cuộc sống không có nghĩa là dễ dàng. Nó luôn luôn biến động.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Trong cuộc sống đừng chờ đợi sự may mắn, mà hãy thực hiện và cũng đừng sợ sự thất bại, nếu bạn sợ, bạn sẽ chẳng làm được việc gì nên hồn đâu.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Hãy luôn nhớ câu nói \"không có gì là tuyệt đối\". Đừng buồn khi bạn thất bại, sai lầm nhất của con người là không mắc sai lầm.", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("Đừng cố kiếm nhiều tiền, ước mơ cao sang, cố gắng khiến người khác biết bạn giàu có. Hãy cố gắng làm cho cuộc sống của bạn ý nghĩa hơn. Vì bạn không thể mang theo gì khi chết đâu.", null, TYPE_CUOC_SONG, false));


        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));
        listData.add(new CommonCaption("", null, TYPE_CUOC_SONG, false));

        //tình bạn
        listData.add(new CommonCaption("Mày ơi thương lấy tao cùng\nTuy rằng khác lớp nhưng chung một đề\nMày cho tao chép tí đê\nKẻo mai tao rớt mặt mày tím đen", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Đi khắp thế gian không đâu bằng đứa bạn thân khốn nạn", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Nếu là bạn xin mãi mãi là bạn, đừng như sông lúc cạn lúc đầy", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Đúng nghĩa từ bạn\nLà không thêm từ phản\nVà không bắn đạn sau lưng", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn thân là đứa không phân biệt giàu nghèo, đẹp hay xấu, là đứa luôn nói thẳng với tôi chứ không bao giờ nịnh hót hay lừa dối, và là đứa luôn cười toe toét khi tôi bị ngã", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Học hành có nghĩa là dành 10% thời gian nghiên cứu và 90% còn lại để than phiền với đứa bạn thân bạn học khổ sở như thế nào", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn bè ít thôi vừa đủ dùng\nChứ đừng để cả thùng,\nRồi lâm vào đường cùng\nPhút cuối cùng nó mới đi cúng", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Chúng ta sẽ là bạn của nhau mãi mãi bởi vì đã biết quá nhiều về nhau nên không dám chọc nhau giận", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Tình bạn, cũng như tình yêu, bị sự thiếu vắng kéo dài phá hủy, dù nó có thể trở nên mạnh mẽ hơn nhờ những xa cách ngắn tạm thời", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Một kẻ thù không phải là ít, một trăm người bạn chưa phải là nhiều", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn biết đó là bạn thân vì nó là đứa hiểu bạn muốn gì ngay cả khi bạn lầm bầm một thứ ngôn ngữ chết tiệt nào đó", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Sự phê bình của người bạn còn tốt hơn lời nịnh nọt của kẻ thù", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Mỉm cười thì có bạn, nhăn mặt thì có nếp nhăn", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Một ngày cho công việc cực nhọc, một giờ cho thể thao, cả cuộc đời dành cho bạn bè cũng còn quá ngắn ngủi", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Tôi chơi với bạn không phải vì bạn là ai, mà là vì tôi sẽ là người thế nào khi ở bên bạn", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn thân là đứa có đánh chết cũng không khen bạn một câu, nhưng nó luôn nói tốt về bạn sau lưng với đứa khác", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Quá xấu với kẻ thù và ít tử tế với bạn bè đều nguy hiểm như nhau", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Khi gặp nhau:Bạn bình thường: Chào bạn, lâu lắm rồi mới gặp ấy nhỉ?\nBạn thân tri kỷ: Ê thằng cờ hó, vẫn chưa chết à?", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn thân có thể phũ với bạn cả chục lần nhưng nhất định sống chết với ai làm tổn thương bạn dù chỉ một lần", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn thân là đứa để suốt ngày nghe ta phàn nàn oán trách về mọi thứ trong cuộc sống nhưng rồi cuối cùng nó chốt một câu: Kệ mẹ mày chứ", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Tôi không cần lời nói thể hiện, không cần nước mắt để thương cảm, không cần một nụ cười hay cái nắm tay an ủi, tất cả những gì tôi cần là làm bạn của bạn mãi mãi", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Tìm một người bạn thân không khó. Nhưng quan trọng nó là cờ hó hay là người", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Lúc ăn chơi sao không gọi bạn?\nLúc hoạn nạn cứ gọi bạn ơi!", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Bạn sẽ thấy PHÊ dù chỉ uống nước ngọt với lũ bạn thân", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Sống chết có nhau, ốm đau kệ cụ mày", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Xin thề: Tôi với anh kết nghĩa anh em, tuy không sinh cùng năm cùng tháng cùng ngày nhưng nguyện sống cùng ngày cùng tháng cùng năm", null, TYPE_BAN_BE, false));

        /**
         * ĐỨC: END
         */

        return listData;
    }
}
