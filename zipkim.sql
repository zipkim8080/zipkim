create table property (
    no INTEGER AUTO_INCREMENT PRIMARY KEY,
    pro_name VARCHAR(20) NOT NULL
);



create table property_doc (
    no INTEGER AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(256) NOT NULL,
    path VARCHAR(256) NOT NULL,
    content_type VARCHAR(56),
    size INTEGER,
    reg_date DATETIME DEFAULT now(),
    CONSTRAINT FOREIGN KEY(no) references property(no)
);