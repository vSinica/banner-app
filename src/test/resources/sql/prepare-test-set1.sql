insert into public.category (deleted, name, req_name)
values  (false, 'category1', 'category1reqId'),
        (false, 'category2', 'category2reqId');

insert into public.banners (content, deleted, name, price, category)
values  ('banner1Text', false, 'banner1Name', 222, 1),
        ('banner4Text', false, 'banner4Name', 555, 1);

insert into public.requests (datetime, ip_address, user_agent, banner)
                values ('2022-11-02 00:24:21.000000', '172.21.0.1', 'Apache-HttpClient/4.5.13 (Java/17.0.4)', 1);


