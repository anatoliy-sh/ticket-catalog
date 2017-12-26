
insert into ticket

select ceil(random() * 1000000), ceil(random() * 10000), timestamp '2014-01-10 20:00:00' +
       random() * (timestamp '2014-01-20 20:00:00' -
                   timestamp '2014-01-10 10:00:00'),
md5(random()::text),
md5(random()::text),
'free'