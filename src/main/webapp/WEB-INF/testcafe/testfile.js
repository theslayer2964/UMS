import { Selector } from 'testcafe';

const query = process.env.QUERY_PARAM;

fixture `Google Search`
    .page `https://www.google.com`;

test('Search TestCafe', async t => {
    await t
        .typeText('input[name="q"]', "Ahihi")
        .pressKey('enter')
        .expect(Selector('#search').exists).ok();
});