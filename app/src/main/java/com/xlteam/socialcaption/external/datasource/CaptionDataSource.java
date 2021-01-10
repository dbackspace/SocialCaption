package com.xlteam.socialcaption.external.datasource;

import com.xlteam.socialcaption.model.CommonCaption;

import java.util.ArrayList;
import java.util.List;

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

    public List<CommonCaption> getDataFirstTime() {
        List<CommonCaption> listData = new ArrayList<>();
        listData.add(new CommonCaption("Đồ rê mi pha son la si mê Anh", null, 1, false));
        listData.add(new CommonCaption("Trời thu đẹp nhất về đêm\nĐời em đẹp nhất là thêm anh vào", null, 1, false));
        listData.add(new CommonCaption("Chỉ cần anh muốn hẹn hò\nNgười xinh, cảnh đẹp, cuộc tình em lo", null, 1, false));
        listData.add(new CommonCaption("Đừng gọi em là thiếu nữ\ntrong khi thứ em thiếu là đàn ông", null, 1, false));
        listData.add(new CommonCaption("Đêm nay ánh trăng mập mờ\nGiường anh có rộng em ngủ nhờ một đêm...", null, 1, false));
        listData.add(new CommonCaption("Gặp anh em bỗng muốn thờ\nKhông thờ ích thích mà thờ ương thương", null, 1, false));
        listData.add(new CommonCaption("Thích em thì hôn má, còn mà thích quá thì mình hôn môi", null, 1, false));
        listData.add(new CommonCaption("Sáng anh nhớ tôi thì tối anh nhớ sang", null, 1, false));
        listData.add(new CommonCaption("Sống ảo thì em có, nhưng sống cùng ai đó thì em chưa", null, 1, false));
        listData.add(new CommonCaption("Anh mặc gì cũng được nhưng không được mặc kệ em", null, 1, false));
        listData.add(new CommonCaption("Tóc em màu đen, môi em màu đỏ\nLại gần em hỏi nhỏ, anh có nhớ em không?", null, 1, false));
        listData.add(new CommonCaption("Em vốn dĩ yêu bản thân mình\nNhưng giờ lại muốn ngoại tình với anh", null, 1, false));
        listData.add(new CommonCaption("Trời sinh yếu đuối mỏng manh\nNên cần được tựa vai anh mỗi ngày", null, 1, false));
        listData.add(new CommonCaption("Cảm lạnh là do gió, còn cảm nắng chắc chắn là do anh", null, 1, false));
        listData.add(new CommonCaption("Anh xem em đã đủ ngầu\nĐể anh quên mối tình đầu hay chưa?", null, 1, false));
        listData.add(new CommonCaption("Đêm Hà Nội sương mù bao phủ\nNhớ anh nhiều em có ngủ được đâu", null, 1, false));
        listData.add(new CommonCaption("Thanh xuân như một chén trà\nQuanh năm uống rượu nên trà còn nguyên", null, 1, false));
        listData.add(new CommonCaption("Muốn tìm mật ngọt trên đời\nThì mình phải tập tin lời đàn ông", null, 1, false));
        listData.add(new CommonCaption("Tính em rất tồi\nNhưng tối em rất tình", null, 1, false));
        listData.add(new CommonCaption("Sống ảo thì em có\nNhưng sống cùng ai đó thì em chưa", null, 1, false));
        listData.add(new CommonCaption("Gọi anh là mì gói, vì em muốn ăn liền", null, 1, false));
        listData.add(new CommonCaption("Trời xanh gió mát chẳng ngừng\nAnh không cẩn thận coi chừng iu em", null, 1, false));
        listData.add(new CommonCaption("Em vụng về làm gì cũng đổ\nTấm thân này cũng lỡ đổ vào anh", null, 1, false));
        listData.add(new CommonCaption("Đừng như Hà Lan và Ngạn\nVì em không muốn chúng ta là bạn", null, 1, false));
        listData.add(new CommonCaption("Thời tiết trái gió dở trời\nCần người trái giới dở trò", null, 1, false));
        listData.add(new CommonCaption("Em không có ba vòng căng đét\nNhưng nhan sắc này đủ tạo nét chưa anh", null, 1, false));
        listData.add(new CommonCaption("Đó giờ chỉ toàn ăn cơm với cá\nEm làm gì biết thơm má ai đâu", null, 1, false));
        listData.add(new CommonCaption("Anh thích con gái dễ thương hay quyến rũ, hỏi thế cho vui chứ em đây có đủ", null, 1, false));
        listData.add(new CommonCaption("Em dửng dưng trước vạn người là thế, nhưng lại yếu đuối trước sự tử tế của anh", null, 1, false));
        listData.add(new CommonCaption("Bình minh thì ở phía Đông, bình yên thì ở phía em đây nè", null, 1, false));
        listData.add(new CommonCaption("Nếu sốt cao thì gọi cấp cứu, nếu nhớ em nhiều, chờ xíu em qua", null, 1, false));
        listData.add(new CommonCaption("Có những ngày mệt chẳng muốn làm gì cả, chỉ muốn làm người yêu anh", null, 1, false));
        listData.add(new CommonCaption("Lồng thì em để nhốt chim\nCòn anh em nhốt trong tim em này", null, 1, false));
        listData.add(new CommonCaption("Bệnh corona thì tìm bác sĩ\nBệnh codonqua thì tìm đến em.", null, 1, false));
        listData.add(new CommonCaption("Thân thiện với môi trường\nTừ trường với môi anh.", null, 1, false));
        listData.add(new CommonCaption("Ngày xưa em thích màu xanh dương\nngày nay em thích anh người em thương", null, 1, false));
        listData.add(new CommonCaption("Bột ngọt thì ngọt nhưng gây ung thư\nCòn anh cũng ngọt nhưng gây tương tư", null, 1, false));
        listData.add(new CommonCaption("Không mơ cổ tích hoang đường\nChỉ mơ giấc mộng đời thường có anh", null, 1, false));
        listData.add(new CommonCaption("Em đã bị nhiễm một căn bệnh gọi là yêu, và em tin rằng em đã mắc nó từ anh", null, 1, false));
        listData.add(new CommonCaption("Em có một siêu năng lực là nhắm mắt lại là có thể nhìn thấy anh", null, 1, false));
        listData.add(new CommonCaption("Em yêu bầu trời xanh, yêu cả cánh hoa hồng. Nhưng không quên một thứ, đó chính là yêu anh", null, 1, false));
        listData.add(new CommonCaption("Romeo thì có Juliet, Jack thì có Rose, còn anh thì có em", null, 1, false));
        listData.add(new CommonCaption("Tính mời anh đi ăn tối nhưng lại sợ thành bữa tối của anh", null, 1, false));
        listData.add(new CommonCaption("Thay vì tặng anh một đóa hồng không héo, để em gửi anh một mối tình không phai", null, 1, false));
        listData.add(new CommonCaption("Anh có biết cơ thể anh được tạo thành 70% từ nước không? và em cảm thấy rất khát ngay bây giờ", null, 1, false));
        listData.add(new CommonCaption("Thức khuya em tỉnh bằng trà, yêu anh em trả bằng tình được không?", null, 1, false));
        listData.add(new CommonCaption("Em đoán tên anh là Google. Bởi vì anh đang có mọi thứ mà em đang tìm kiếm", null, 1, false));
        listData.add(new CommonCaption("Em nghĩ điện thoại của em gặp vấn đề rồi, nó chưa có số điện thoại của anh", null, 1, false));
        listData.add(new CommonCaption("Hà Nội không thể vì hoàng hôn mà trở nên rực rỡ. Nhưng em có thể vì anh mà trở nên yêu đời", null, 1, false));
        listData.add(new CommonCaption("Anh ở ngoài tầm mắt của em, nhưng luôn ở trong tâm trí của em.", null, 1, false));
        listData.add(new CommonCaption("Em ước anh là tấm gương của em, để em có thể nhìn anh vào mỗi sáng", null, 1, false));
        listData.add(new CommonCaption("Nếu mỗi lần nghĩ về anh em được một thanh kẹo, chắc em sẽ béo lên nhanh chóng mất", null, 1, false));
        listData.add(new CommonCaption("Em từng nghĩ nơi hạnh phúc nhất là Thiên Đường, nhưng đó là trước khi em ở bên anh", null, 1, false));
        listData.add(new CommonCaption("Hoa hồng màu đỏ, Violet thì màu xanh, còn em thì yêu anh", null, 1, false));
        listData.add(new CommonCaption("Mặt trời vừa mọc hay anh chỉ vừa cười với em?", null, 1, false));
        listData.add(new CommonCaption("Mọi người thường nói không có gì là vĩnh cửu, nhưng đó là trước khi tình yêu của chúng mình xuất hiện", null, 1, false));
        listData.add(new CommonCaption("Em luôn có nhiều con đường lớn để đi, nhưng em chọn đi theo lối nhỏ dẫn đến anh", null, 1, false));
        listData.add(new CommonCaption("Chơi trò tung đồng xu đi anh, nếu mặt ngửa anh phải yêu em, nếu mặt sấp, em sẽ bị anh yêu", null, 1, false));
        listData.add(new CommonCaption("Nếu anh là người vô gia cư, hãy chuyển vào sống trong tim em này, miễn phí đấy", null, 1, false));
        return listData;
    }
}
