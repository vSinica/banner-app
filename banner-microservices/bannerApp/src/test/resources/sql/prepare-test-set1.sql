INSERT INTO public.category (id, deleted, name, req_name) VALUES (1,false, 'category1', 'category1reqId');
INSERT INTO public.category (id, deleted, name, req_name) VALUES (2,false, 'category2', 'category2reqId');


INSERT INTO public.banners (id, content, deleted, name, price, category) VALUES (1, 'banner1Text', false, 'banner1Name', 222, 1);
INSERT INTO public.banners (id, content, deleted, name, price, category) VALUES (2, 'banner4Text', false, 'banner4Name', 555, 1);

insert into public.requests (id, datetime, ip_address, user_agent, banner)
                values (1, '2022-11-02 00:24:21.000000', '172.21.0.1', 'Apache-HttpClient/4.5.13 (Java/17.0.4)', 1);


