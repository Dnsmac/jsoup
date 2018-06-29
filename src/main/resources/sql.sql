CREATE TABLE `dealer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(20) DEFAULT NULL COMMENT '店名',
  `dealer_name` varchar(20) DEFAULT NULL COMMENT '品牌名称',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `promotion` varchar(50) DEFAULT NULL COMMENT '促销',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4
