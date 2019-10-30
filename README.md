Giao tiếp với server tại cổng 1108, theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên theo định dạng “;studentCode”. Ví dụ:“;B15DCCN001;101”
b. Nhận thông điệp từ server chứa requestId là một chuỗi ngẫu nhiên duy nhất và một
chuỗi ngẫu nhiên data cần xử lý theo định dạng “requestId; data”
c. Xử lý tách chuỗi đã nhận thành 2 chuỗi
i. Chuỗi thứ nhất gồm các ký tự và số (loại bỏ các ký tự đặc biệt)
ii. Chuỗi thứ hai gồm các ký tự đặc biệt
Và thực hiện gửi thông điệp lên server theo định dạng “requestId;str1,str2”
d. Đóng socket và kết thúc