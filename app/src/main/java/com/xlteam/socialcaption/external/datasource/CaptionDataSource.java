package com.xlteam.socialcaption.external.datasource;

import android.content.Context;

import com.xlteam.socialcaption.external.utility.SharePreUtils;
import com.xlteam.socialcaption.model.CommonCaption;

import java.util.ArrayList;
import java.util.List;

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
        int currentVersion = SharePreUtils.getVersionLocalDatabase(context); // default là 0
        List<CommonCaption> listData = new ArrayList<>();
        if (currentVersion < 1) {
            listData.addAll(getDataFirstTime());
            SharePreUtils.setVersionLocalDatabase(context, 1);
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
        listData.add(new CommonCaption("Tính em rất tồi\nNhưng tối em rất tình", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Sống ảo thì em có\nNhưng sống cùng ai đó thì em chưa", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Gọi anh là mì gói, vì em muốn ăn liền", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Trời xanh gió mát chẳng ngừng\nAnh không cẩn thận coi chừng iu em", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em vụng về làm gì cũng đổ\nTấm thân này cũng lỡ đổ vào anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đừng như Hà Lan và Ngạn\nVì em không muốn chúng ta là bạn", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thời tiết trái gió dở trời\nCần người trái giới dở trò", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em không có ba vòng căng đét\nNhưng nhan sắc này đủ tạo nét chưa anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Đó giờ chỉ toàn ăn cơm với cá\nEm làm gì biết thơm má ai đâu", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh thích con gái dễ thương hay quyến rũ, hỏi thế cho vui chứ em đây có đủ", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em dửng dưng trước vạn người là thế, nhưng lại yếu đuối trước sự tử tế của anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Bình minh thì ở phía Đông, bình yên thì ở phía em đây nè", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Nếu sốt cao thì gọi cấp cứu, nếu nhớ em nhiều, chờ xíu em qua", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Có những ngày mệt chẳng muốn làm gì cả, chỉ muốn làm người yêu anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Lồng thì em để nhốt chim\nCòn anh em nhốt trong tim em này", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Bệnh corona thì tìm bác sĩ\nBệnh codonqua thì tìm đến em.", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thân thiện với môi trường\nTừ trường với môi anh.", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Ngày xưa em thích màu xanh dương\nngày nay em thích anh người em thương", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Bột ngọt thì ngọt nhưng gây ung thư\nCòn anh cũng ngọt nhưng gây tương tư", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Không mơ cổ tích hoang đường\nChỉ mơ giấc mộng đời thường có anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em đã bị nhiễm một căn bệnh gọi là yêu, và em tin rằng em đã mắc nó từ anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em có một siêu năng lực là nhắm mắt lại là có thể nhìn thấy anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em yêu bầu trời xanh, yêu cả cánh hoa hồng. Nhưng không quên một thứ, đó chính là yêu anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Romeo thì có Juliet, Jack thì có Rose, còn anh thì có em", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Tính mời anh đi ăn tối nhưng lại sợ thành bữa tối của anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thay vì tặng anh một đóa hồng không héo, để em gửi anh một mối tình không phai", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh có biết cơ thể anh được tạo thành 70% từ nước không? và em cảm thấy rất khát ngay bây giờ", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Thức khuya em tỉnh bằng trà, yêu anh em trả bằng tình được không?", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em đoán tên anh là Google. Bởi vì anh đang có mọi thứ mà em đang tìm kiếm", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em nghĩ điện thoại của em gặp vấn đề rồi, nó chưa có số điện thoại của anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Hà Nội không thể vì hoàng hôn mà trở nên rực rỡ. Nhưng em có thể vì anh mà trở nên yêu đời", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Anh ở ngoài tầm mắt của em, nhưng luôn ở trong tâm trí của em.", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em ước anh là tấm gương của em, để em có thể nhìn anh vào mỗi sáng", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Nếu mỗi lần nghĩ về anh em được một thanh kẹo, chắc em sẽ béo lên nhanh chóng mất", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em từng nghĩ nơi hạnh phúc nhất là Thiên Đường, nhưng đó là trước khi em ở bên anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Hoa hồng màu đỏ, Violet thì màu xanh, còn em thì yêu anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Mặt trời vừa mọc hay anh chỉ vừa cười với em?", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Mọi người thường nói không có gì là vĩnh cửu, nhưng đó là trước khi tình yêu của chúng mình xuất hiện", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Em luôn có nhiều con đường lớn để đi, nhưng em chọn đi theo lối nhỏ dẫn đến anh", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Chơi trò tung đồng xu đi anh, nếu mặt ngửa anh phải yêu em, nếu mặt sấp, em sẽ bị anh yêu", null, TYPE_THA_THINH, false));
        listData.add(new CommonCaption("Nếu anh là người vô gia cư, hãy chuyển vào sống trong tim em này, miễn phí đấy", null, TYPE_THA_THINH, false));

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
        listData.add(new CommonCaption("Một ngày cho công việc cực nhọc, một giờ cho thể thao, cả cuộc đời dành cho bạn bè cũng còn quá ngắn ngủ", null, TYPE_BAN_BE, false));
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
        listData.add(new CommonCaption("Sống chết có nhau, ốm đâu kệ cụ mày", null, TYPE_BAN_BE, false));
        listData.add(new CommonCaption("Xin thề: Tôi với anh kết nghĩa anh em., tuy không sinh cùng năm cùng tháng cùng ngày nhưng nguyện sống cùng ngày cùng tháng cùng năm", null, TYPE_BAN_BE, false));
        return listData;
    }
}
