from pony.orm import *

import sys
import json
import datetime
import logging


logger = logging.getLogger('logger')
db = Database()


class Category(db.Entity):
    category_id = PrimaryKey(int, auto=True)
    category_name = Required(str)
    article = Set("Article")


class AuxiliaryText(db.Entity):
    auxiliary_text_id = PrimaryKey(int, auto=True)
    text = Optional(str)
    article = Required("Article")


class Article(db.Entity):
    article_id = PrimaryKey(int, auto=True)
    title = Required(str, nullable=True)
    language = Required(str)
    wiki = Required(str)
    create_timestamp = Required(datetime.datetime)  # datetime
    timestamp = Required(datetime.datetime)

    category = Set(Category)
    auxiliary_text = Set(AuxiliaryText)


db.bind(provider='postgres', user='postgres', password='root', host='db', database='postgres', port="5432")
db.generate_mapping(create_tables=True)


@db_session
def main(file=r"ruwikiquote-20220530-cirrussearch-general.json"):
    with open(file, "r") as read_file:
        try:
            for number, line in enumerate(read_file.readlines()):
                if "index" in line:
                    continue

                logger.info(f"Importing article with number {number}...")

                data = json.loads(line)
                current_article = Article(title=data["title"],
                                          language=data["language"],
                                          wiki=data["wiki"],
                                          create_timestamp=datetime.datetime.fromisoformat(data["create_timestamp"][:-1]),
                                          timestamp=datetime.datetime.fromisoformat(data["timestamp"][:-1]))

                categories = []
                for category in data["category"]:
                    categories.append(Category(category_name=category, article=current_article))
                current_article.category = categories

                if "auxiliary_text" in data:
                    if len(data["auxiliary_text"]) == 0:
                        auxiliary_text = AuxiliaryText(article=current_article)
                    else:
                        auxiliary_text = []
                        for text in data["auxiliary_text"]:
                            auxiliary_text.append(AuxiliaryText(text=text, article=current_article))
                else:
                    auxiliary_text = AuxiliaryText(article=current_article)

                commit()
            logger.info("Import was successful")
        except Exception as ex:
            logger.error(f"Something went wrong while importing data into the database: {ex}")


if __name__ == "__main__":
    json_file = sys.argv[1] if len(sys.argv) >= 1 else "ruwikiquote-20220530-cirrussearch-general.json"
    main(json_file)
