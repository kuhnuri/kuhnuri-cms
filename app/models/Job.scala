package models

import java.io.{File, InputStream, OutputStream}

sealed case class Job(id: String)

sealed case class Create(id: String, data: InputStream)

sealed case class Update(id: String, data: InputStream, version: Boolean)

sealed case class Resource(id: String, data: File)
